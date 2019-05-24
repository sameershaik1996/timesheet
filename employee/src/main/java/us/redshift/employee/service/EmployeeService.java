package us.redshift.employee.service;

import org.hibernate.JDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import us.redshift.employee.domain.Employee;
import us.redshift.employee.dto.EmployeeDto;
import us.redshift.employee.repository.EmployeeRespository;
import us.redshift.employee.util.DTO;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class EmployeeService implements IEmployeeService {

    @Autowired
    EmployeeRespository employeeRespository;

    @Override
    public Employee createEmployee(Employee employee)  throws ConstraintViolationException,DataIntegrityViolationException {
        try {
            Employee emp = employeeRespository.findTopByOrderByIdDesc();
            employee.setEmployeeId(generateEmployeeId(emp));
            return employeeRespository.save(employee);
        }
        catch (ConstraintViolationException e){
            throw e;
        }
        catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("Value Already Exists",e);
        }
    }


    @Override
    public Employee updateEmployee(Employee employee) {
        return employeeRespository.save(employee);
    }

    @Override
    public Employee getEmployeeById(Long id)throws NoSuchElementException, EntityNotFoundException {
        try{
            Employee employee= employeeRespository.findById(id).get();
            return employee;
        }
        catch (NoSuchElementException ex){

            throw new NoSuchElementException( "Employee not found");
        }
    }

    @Override
    public List<Employee> getAllEmployee() {
        return employeeRespository.findAll();
    }

    @Override
    public Boolean checkIfEmployeeExists(Long id) {
        return employeeRespository.existsById(id);
    }

    @Override
    public List<Employee> getEmployeeByIds(List<Long> id) {
        return employeeRespository.findByIdIn(id);
    }

    private String generateEmployeeId(Employee emp) {

        String s ;
        if(emp==null)
            s=String.format("RS_%04d", 1);
        else
        {
            int i=Integer.parseInt(emp.getEmployeeId().substring(3));
            i++;
            s=String.format("RS_%04d",i);
        }
        return s;

    }

}
