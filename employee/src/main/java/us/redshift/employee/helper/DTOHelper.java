package us.redshift.employee.helper;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.redshift.employee.domain.Employee;
import us.redshift.employee.dto.EmployeeDto;

@Component
public class DTOHelper implements IDTOHelper{
    @Autowired
    ModelMapper modelMapper;

    @Override
    public EmployeeDto employeToDto(Employee employee) {
        return modelMapper.map(employee, EmployeeDto.class);
    }

    @Override
    public Employee employeeToEntity(EmployeeDto employeeDto) {
        return modelMapper.map(employeeDto, Employee.class);
    }


}
