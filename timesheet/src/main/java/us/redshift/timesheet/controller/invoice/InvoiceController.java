package us.redshift.timesheet.controller.invoice;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import us.redshift.timesheet.domain.invoice.Invoice;
import us.redshift.timesheet.service.invoice.IInvoiceService;

@RestController
@RequestMapping("timesheet/v1/api/invoice")
public class InvoiceController {

    private final IInvoiceService invoiceService;

    public InvoiceController(IInvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }


    @PostMapping("save")
    public ResponseEntity<?> createInvoice(@RequestBody Invoice invoice, @RequestParam(value = "fromDate", required = false) String fromDate) {
        return new ResponseEntity<>(invoiceService.createInvoice(invoice), HttpStatus.CREATED);
    }

    @PutMapping("update")
    public ResponseEntity<?> updateInvoice(@RequestBody Invoice invoice) {
        if (invoice.getId() == null)
            return new ResponseEntity<>(new ResponseStatusException(HttpStatus.BAD_REQUEST, "id cannot be null"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(invoiceService.updateInvoice(invoice), HttpStatus.CREATED);
    }

    @GetMapping("get")
    public ResponseEntity<?> getInvoice(
            @RequestParam(value = "projectId", defaultValue = "0", required = false) Long projectId,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limits", defaultValue = "0") Integer limits,
            @RequestParam(value = "orderBy", defaultValue = "ASC", required = false) String orderBy,
            @RequestParam(value = "fields", defaultValue = "id", required = false) String... fields) {
        if (projectId != 0) {
            return new ResponseEntity<>(invoiceService.getAllInvoiceByProjectId(projectId, page, limits, orderBy, fields), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(invoiceService.getAllInvoiceByPagination(page, limits, orderBy, fields), HttpStatus.OK);
        }
    }

    @GetMapping("get/{id}")
    public ResponseEntity<?> getInvoiceById(@PathVariable Long id) {
        return new ResponseEntity<>(invoiceService.getInvoiceById(id), HttpStatus.OK);
    }


}
