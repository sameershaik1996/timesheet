package us.redshift.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import us.redshift.employee.domain.Designation;

import java.util.List;

public interface DesignationRepository extends JpaRepository<Designation, Long> {

    @Query(value = "select distinct(id) from emp_designations where designation like %:designation%",nativeQuery = true)
    List<Long> findIdByDesignation(String designation);

}
