package us.redshift.timesheet.reposistory.invoice;

import org.springframework.data.jpa.repository.JpaRepository;
import us.redshift.timesheet.domain.invoice.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
