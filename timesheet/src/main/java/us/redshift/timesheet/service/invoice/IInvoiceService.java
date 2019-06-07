package us.redshift.timesheet.service.invoice;

import org.springframework.data.domain.Page;
import us.redshift.timesheet.domain.invoice.Invoice;

public interface IInvoiceService {


    Invoice createInvoice(Invoice invoice);

    Invoice updateInvoice(Invoice invoice);

    Invoice getInvoiceById(Long id);

    Page<Invoice> getAllInvoiceByPagination(int page, int limits, String orderBy, String... fields);

    Page<Invoice> getAllInvoiceByProjectId(Long projectId, int page, int limits, String orderBy, String... fields);

}
