package us.redshift.timesheet.reposistory;

import org.springframework.data.jpa.repository.JpaRepository;
import us.redshift.timesheet.domain.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice,Long> {
}
