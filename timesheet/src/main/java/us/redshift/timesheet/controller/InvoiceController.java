package us.redshift.timesheet.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import us.redshift.timesheet.domain.Invoice;
import us.redshift.timesheet.reposistory.InvoiceRepository;
import us.redshift.timesheet.service.IInvoiceService;

@RestController
@RequestMapping("/timesheet/v1/api/invoice")
public class InvoiceController {

    @Autowired
    private IInvoiceService invoiceService;

    @PostMapping("save")
    public ResponseEntity<?> createInvoice(@RequestBody Invoice invoice)
    {
        return new ResponseEntity<>(invoiceService.createInvoice(invoice), HttpStatus.CREATED);
    }

    @PutMapping("update")
    public ResponseEntity<?> updateInvoice(@RequestBody Invoice invoice)
    {
        return  new ResponseEntity<>(invoiceService.updateInvoice(invoice),HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getInvoiceById(@PathVariable Long id)
    {
        return  new ResponseEntity<>(invoiceService.getInvoiceById(id),HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllInvoices()
    {
        return  new ResponseEntity<>(invoiceService.getAllInvoices(),HttpStatus.OK);
    }


}
