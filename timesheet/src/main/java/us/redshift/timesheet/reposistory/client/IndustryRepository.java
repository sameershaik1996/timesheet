package us.redshift.timesheet.reposistory.client;

import org.springframework.data.jpa.repository.JpaRepository;
import us.redshift.timesheet.domain.client.Industry;

import java.util.List;

public interface IndustryRepository extends JpaRepository<Industry, Long> {

    List<Industry> findAllByOrderByNameAsc();
}
