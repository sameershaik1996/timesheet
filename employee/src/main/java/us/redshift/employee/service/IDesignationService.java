package us.redshift.employee.service;

import us.redshift.employee.domain.Designation;
import us.redshift.employee.dto.DesignationDto;

import java.util.List;

public interface IDesignationService {

    Designation createDesignation(Designation designation);

    Designation updateDesignation(Designation designation);

    List<Designation> getAllDesignation();

    Designation getDesignationById(Long id);
}
