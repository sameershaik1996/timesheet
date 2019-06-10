package us.redshift.timesheet.domain.invoice;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import us.redshift.timesheet.domain.common.BaseEntity;
import us.redshift.timesheet.domain.task.Task;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pss_invoice_details")
public class InvoiceDetails extends BaseEntity implements Serializable {


    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    private String role;

    private String designation;

    private String location;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "invoice_id")
    @JsonIgnoreProperties(value = {"invoiceDetails"})
    private Invoice invoice;

    private BigDecimal hours;


    private BigDecimal ratePerHour;

    private BigDecimal taskAmount;


}
