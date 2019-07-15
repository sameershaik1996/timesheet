package us.redshift.employee.service;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.JDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import us.redshift.employee.Reusable;
import us.redshift.employee.domain.Employee;
import us.redshift.employee.domain.common.EmployeeStatus;
import us.redshift.employee.dto.EmployeeDto;
import us.redshift.employee.exception.CustomException;
import us.redshift.employee.feignclient.IUserClient;
import us.redshift.employee.repository.EmployeeRespository;
import us.redshift.employee.util.DTO;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class EmployeeService implements IEmployeeService {

    @Autowired
    EmployeeRespository employeeRespository;

    @Autowired
    IUserClient userClient;

    @Override
    public Employee createEmployee(Employee employee) throws ConstraintViolationException, DataIntegrityViolationException {

        Employee emp = employeeRespository.findTopByOrderByIdDesc();
        employee.setEmployeeId(generateEmployeeId(emp));
        return employeeRespository.save(employee);

    }


    @Override
    public Employee updateEmployee(Employee employee) {
        if (employee.getReportingManager() != null) {
            if (employee.getId() == employee.getReportingManager().getId()) {
                throw new CustomException("Same Employee can't be assigned as Reporting Manager ");
            }
        }

        return employeeRespository.save(employee);
    }

    @Override
    public Employee getEmployeeById(Long id) throws NoSuchElementException, EntityNotFoundException {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        //System.out.println(request.getHeader("Authorization"));
        try {
            Employee employee = employeeRespository.findById(id).get();
            return employee;
        } catch (NoSuchElementException ex) {

            throw new NoSuchElementException("Employee "+id+"not found");
        }
    }

    @Override
    public Page<Employee> getAllEmployee(int page, int limits, String orderBy, String... fields) {
        Pageable pageable = Reusable.paginationSort(page, limits, orderBy, fields);
        Page<Employee> employees = employeeRespository.findAll(pageable);
        return employees;
    }

    @Override
    public Boolean checkIfEmployeeExists(Long id) {
        return employeeRespository.existsById(id);
    }

    @Override
    public List<Employee> getEmployeeByIds(List<Long> id) {
        return employeeRespository.findByIdIn(id);
    }

    @Override
    public List<Employee> findByIdNotLike(Long id) {
        return employeeRespository.findByIdNotLike(id);
    }

    @Override
    public int setStatusForEmployee(EmployeeStatus status, List<Long> empIds) {
        return employeeRespository.setStatusForEmployee(status.toString(), empIds);
    }

    @Override
    @Scheduled(cron = "0 0 12 * * ?")
    public void markEmployeeAsInActive() {
        List<Employee> employees=employeeRespository.findByLastWorkingDateLessThanAndStatus(DateUtils.truncate(new Date(), java.util.Calendar.DAY_OF_MONTH),EmployeeStatus.ACTIVE);
        List<Long> ids=new ArrayList<>();
        for (Employee emp:employees) {
            ids.add(emp.getId());
        }
        setStatusForEmployee(EmployeeStatus.INACTIVE, ids);
        userClient.updateUser(ids, false);
    }

    private String generateEmployeeId(Employee emp) {

        String s;
        if (emp == null)
            s = String.format("RS_%04d", 1);
        else {
            int i = Integer.parseInt(emp.getEmployeeId().substring(3));
            i++;
            s = String.format("RS_%04d", i);
        }
        return s;

    }

}
