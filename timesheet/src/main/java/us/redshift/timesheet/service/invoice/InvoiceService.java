package us.redshift.timesheet.service.invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.redshift.timesheet.domain.invoice.Invoice;
import us.redshift.timesheet.domain.invoice.InvoiceDetails;
import us.redshift.timesheet.reposistory.invoice.InvoiceRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceService implements IInvoiceService {

    @Autowired
    InvoiceRepository invoiceRepository;

    @Override
    public Invoice createInvoice(Invoice invoice) {

        List<InvoiceDetails> invoiceDetails = new ArrayList<>(invoice.getInvoiceDetails());
        invoiceDetails.forEach(invoiceDetail -> {
            invoice.addInvoiceDetail(invoiceDetail);
        });
        return invoiceRepository.save(invoice);
    }

    @Override
    public Invoice updateInvoice(Invoice invoice) {
        List<InvoiceDetails> invoiceDetails = new ArrayList<>(invoice.getInvoiceDetails());
        invoiceDetails.forEach(invoiceDetail -> {
            invoice.addInvoiceDetail(invoiceDetail);
        });
        return invoiceRepository.save(invoice);
    }

    @Override
    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.findById(id).get();
    }

    @Override
    public List<Invoice> getAllInvoice() {
        return invoiceRepository.findAll();
    }
}
