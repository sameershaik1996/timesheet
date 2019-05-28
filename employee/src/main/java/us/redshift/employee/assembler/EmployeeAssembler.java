package us.redshift.employee.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.redshift.employee.domain.Employee;
import us.redshift.employee.dto.EmployeeDto;
import us.redshift.employee.feignclient.IUserClient;
import us.redshift.filter.model.UserDetails;
@Component
public class EmployeeAssembler {

    @Autowired
    ModelMapper mapper;

    @Autowired
    IUserClient userClient;
    public EmployeeDto convertToDto(Employee employee){

        EmployeeDto employeeDto=mapper.map(employee,EmployeeDto.class);
        employeeDto.setRole(userClient.getUser(employee.getId()).getRole());
        return  employeeDto;

    }

    public Employee convertToEntity(EmployeeDto employeeDto){

        Employee employee=mapper.map(employeeDto,Employee.class);
        return  employee;

    }

}
