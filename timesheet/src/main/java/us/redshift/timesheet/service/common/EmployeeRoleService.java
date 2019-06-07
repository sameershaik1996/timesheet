package us.redshift.timesheet.service.common;

import org.springframework.stereotype.Service;
import us.redshift.timesheet.domain.EmployeeRole;
import us.redshift.timesheet.exception.ResourceNotFoundException;
import us.redshift.timesheet.reposistory.common.EmployeeRoleRepository;

import java.util.List;

@Service
public class EmployeeRoleService implements IEmployeeRoleService {

    private final EmployeeRoleRepository employeeRoleRepository;

    public EmployeeRoleService(EmployeeRoleRepository employeeRoleRepository) {
        this.employeeRoleRepository = employeeRoleRepository;
    }


    @Override
    public EmployeeRole saveEmployeeRole(EmployeeRole employeeRole) {
        return employeeRoleRepository.save(employeeRole);
    }

    @Override
    public EmployeeRole UpdateEmployeeRole(EmployeeRole employeeRole) {
        if (employeeRoleRepository.existsById(employeeRole.getId()))
            throw new ResourceNotFoundException("EmployeeRole", "Id", employeeRole.getId());
        return employeeRoleRepository.save(employeeRole);
    }

    @Override
    public List<EmployeeRole> getAllEmployeeRole() {
        return employeeRoleRepository.findAll();
    }

    @Override
    public EmployeeRole getEmployeeRoleById(Long id) {
        if (employeeRoleRepository.existsById(id))
            throw new ResourceNotFoundException("EmployeeRole", "Id", id);
        return employeeRoleRepository.findById(id).get();
    }
}
