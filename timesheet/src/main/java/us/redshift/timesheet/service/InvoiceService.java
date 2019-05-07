package us.redshift.timesheet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.redshift.timesheet.domain.Invoice;
import us.redshift.timesheet.domain.InvoiceDetail;
import us.redshift.timesheet.domain.InvoiceStatus;
import us.redshift.timesheet.domain.RateCardDetail;
import us.redshift.timesheet.reposistory.InvoiceRepository;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class InvoiceService implements IInvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Override
    public Invoice createInvoice(Invoice invoice) {
        Set<InvoiceDetail> invoiceDetails = new HashSet<>(invoice.getInvoiceDetails());
        invoiceDetails.forEach(invoiceDetail -> invoice.addInvoiceDetails(invoiceDetail));
        return invoiceRepository.save(invoice);
    }

    @Override
    public Invoice updateInvoice(Invoice invoice) {
        Set<InvoiceDetail> invoiceDetails = new HashSet<>(invoice.getInvoiceDetails());
        invoiceDetails.forEach(invoiceDetail -> invoice.addInvoiceDetails(invoiceDetail));
        return invoiceRepository.save(invoice);
    }

    @Override
    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.findById(id).get();
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }
}
