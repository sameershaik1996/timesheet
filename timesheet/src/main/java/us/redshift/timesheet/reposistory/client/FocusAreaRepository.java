package us.redshift.timesheet.reposistory.client;

import org.springframework.data.jpa.repository.JpaRepository;
import us.redshift.timesheet.domain.client.FocusArea;

import java.util.List;

public interface FocusAreaRepository extends JpaRepository<FocusArea, Long> {

    List<FocusArea> findAllByOrderByCodeAsc();
}
