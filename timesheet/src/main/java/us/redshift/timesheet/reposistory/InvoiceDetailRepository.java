package us.redshift.timesheet.reposistory;

import org.springframework.data.jpa.repository.JpaRepository;
import us.redshift.timesheet.domain.InvoiceDetail;

public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail,Long> {
}
