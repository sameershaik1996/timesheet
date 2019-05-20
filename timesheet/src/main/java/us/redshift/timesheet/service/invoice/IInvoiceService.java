package us.redshift.timesheet.service.invoice;

import us.redshift.timesheet.domain.invoice.Invoice;

import java.util.List;

public interface IInvoiceService {


    Invoice createIvoice(Invoice invoice);

    Invoice updateInvoice(Invoice invoice);

    Invoice getInvoiceById(Long id);

    List<Invoice> getAllInvoice();
}
