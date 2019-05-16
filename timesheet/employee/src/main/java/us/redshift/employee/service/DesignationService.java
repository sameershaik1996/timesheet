package us.redshift.employee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.redshift.employee.domain.Designation;
import us.redshift.employee.dto.DesignationDto;
import us.redshift.employee.repository.DesignationRepository;
import us.redshift.employee.util.DTO;

import javax.transaction.Transactional;
import java.util.List;

@Component
@Transactional
public class DesignationService implements IDesignationService {

    @Autowired
    DesignationRepository designationRepository;

    @Override
    public Designation createDesignation(@DTO(DesignationDto.class)Designation designation) {
        return designationRepository.save(designation);
    }

    @Override
    public Designation updateDesignation(Designation designation) {
        return designationRepository.save(designation);
    }

    @Override
    public List<Designation> getAllDesignation() {
        return designationRepository.findAll();
    }

    @Override
    public Designation getDesignationById(Long id) {
        return designationRepository.findById(id).get();
    }
}
