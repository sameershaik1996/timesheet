package us.redshift.timesheet.service.invoice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import us.redshift.timesheet.domain.invoice.Invoice;
import us.redshift.timesheet.domain.invoice.InvoiceDetails;
import us.redshift.timesheet.domain.timesheet.TimeSheetStatus;
import us.redshift.timesheet.reposistory.invoice.InvoiceRepository;
import us.redshift.timesheet.service.taskcard.ITaskCardDetailService;
import us.redshift.timesheet.util.Reusable;

import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceService implements IInvoiceService {


    private final InvoiceRepository invoiceRepository;

    private final ITaskCardDetailService taskCardDetailService;

    public InvoiceService(InvoiceRepository invoiceRepository, ITaskCardDetailService taskCardDetailService) {
        this.invoiceRepository = invoiceRepository;
        this.taskCardDetailService = taskCardDetailService;
    }

    @Override
    public Invoice createInvoice(Invoice invoice) {
        List<InvoiceDetails> invoiceDetails = new ArrayList<>(invoice.getInvoiceDetails());
        invoiceDetails.forEach(invoiceDetail -> {
            invoice.addInvoiceDetail(invoiceDetail);
        });
        Invoice savedInvoice = invoiceRepository.save(invoice);
        List<Long> taskCardDetailsId = new ArrayList<>();
        invoice.getTaskCardDetails().forEach(taskCardDetail ->
                taskCardDetailsId.add(taskCardDetail.getId())
        );
        int d = taskCardDetailService.setStatusForTaskCardDetail(TimeSheetStatus.INVOICE_RAISED.toString(), taskCardDetailsId);
        return savedInvoice;
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
    public Page<Invoice> getAllInvoiceByPagination(int page, int limits, String orderBy, String... fields) {
        Pageable pageable = Reusable.paginationSort(page, limits, orderBy, fields);
        return invoiceRepository.findAll(pageable);
    }

    @Override
    public Page<Invoice> getAllInvoiceByProjectId(Long projectId, int page, int limits, String orderBy, String... fields) {
        Pageable pageable = Reusable.paginationSort(page, limits, orderBy, fields);
        return invoiceRepository.findAllByProject_IdOrderByFromDateAsc(projectId, pageable);
    }
}
