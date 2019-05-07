package us.redshift.timesheet.service;

import us.redshift.timesheet.domain.Invoice;

import java.util.List;

public interface IInvoiceService {
    Invoice createInvoice(Invoice invoice);

    Invoice updateInvoice(Invoice invoice);

    Invoice getInvoiceById(Long id);

    List<Invoice> getAllInvoices();
}
