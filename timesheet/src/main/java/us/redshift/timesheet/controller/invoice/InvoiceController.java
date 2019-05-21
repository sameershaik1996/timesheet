package us.redshift.timesheet.controller.invoice;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import us.redshift.timesheet.domain.common.Location;
import us.redshift.timesheet.domain.invoice.Invoice;
import us.redshift.timesheet.domain.taskcard.TaskCard;
import us.redshift.timesheet.domain.timesheet.TimeSheetStatus;
import us.redshift.timesheet.reposistory.taskcard.TaskCardDetailRepository;
import us.redshift.timesheet.service.invoice.IInvoiceService;
import us.redshift.timesheet.service.taskcard.ITaskCardDetailService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("timesheet/v1/api/invoice")
public class InvoiceController {

    @Autowired
    IInvoiceService invoiceService;

    @Autowired
    TaskCardDetailRepository taskCardDetailRepository;

    @PostMapping("save")
    public ResponseEntity<?> raiseInvoice(@RequestBody Invoice invoice){
        Invoice savedInvoice=invoiceService.createInvoice(invoice);
        List<Long> taskCardDetailsId=new ArrayList<>();
        invoice.getTaskCardDetails().forEach(taskCardDetail -> {
            taskCardDetailRepository.setStatusForTaskCardDetail(TimeSheetStatus.INVOICE_RAISED.getStatus()  ,taskCardDetail.getId());
        });
        System.out.println(taskCardDetailsId);
        //int d=taskCardDetailRepository.setStatusForTaskCardDetail(TimeSheetStatus.INVOICE_RAISED.getStatus()  ,taskCardDetailsId);
        //System.out.println(d);

        return new ResponseEntity<>(savedInvoice,HttpStatus.CREATED);
    }

    @PutMapping("update")
    public ResponseEntity<?> updateInvoice(@RequestBody Invoice invoice){
        if(invoice.getId()==null)
            return new ResponseEntity<>(new ResponseStatusException(HttpStatus.BAD_REQUEST,"id cannot be null"),HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(invoiceService.updateInvoice(invoice),HttpStatus.CREATED);
    }

    @GetMapping("get")
    public ResponseEntity<?> getInvoice(){
        return new ResponseEntity<>(invoiceService.getAllInvoice(), HttpStatus.OK);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<?> getInvoiceById(@PathVariable Long id){
        return new ResponseEntity<>(invoiceService.getInvoiceById(id), HttpStatus.OK);
    }


}
