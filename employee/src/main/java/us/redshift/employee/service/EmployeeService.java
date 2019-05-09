package us.redshift.employee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.redshift.employee.domain.Employee;
import us.redshift.employee.dto.EmployeeDto;
import us.redshift.employee.repository.EmployeeRespository;
import us.redshift.employee.util.DTO;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class EmployeeService implements IEmployeeService {

    @Autowired
    EmployeeRespository employeeRespository;

    @Override
    public Employee createEmployee(Employee employee) {
        Employee emp=employeeRespository.findTopByOrderByIdDesc();
        employee.setEmployeeId(generateEmployeeId(emp));
        return employeeRespository.save(employee);
    }


    @Override
    public Employee updateEmployee(Employee employee) {
        return employeeRespository.save(employee);
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRespository.findById(id).get();
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
            s=String.format("rs_%04d", 1);
        else
        {
            int i=Integer.parseInt(emp.getEmployeeId().substring(3));
            i++;
            s=String.format("rs_%04d",i);
        }
        return s;

    }

}
