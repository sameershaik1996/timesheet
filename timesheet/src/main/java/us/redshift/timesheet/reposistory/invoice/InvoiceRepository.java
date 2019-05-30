package us.redshift.timesheet.reposistory.invoice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import us.redshift.timesheet.domain.invoice.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    Page<Invoice> findAllByProject_IdOrderByFromDateAsc(Long projectId, Pageable pageable);
}
