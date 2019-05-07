package us.redshift.timesheet.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="pss_invoice_details")
public class InvoiceDetail extends  BaseEntity implements Serializable {

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE},fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id", nullable = false)
    @JsonIgnoreProperties({"invoiceDetails","project","client"})
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "task_card_id", nullable = false)
    @JsonIgnoreProperties({"invoice", "invoiceDetails","project","client","task"})
    private TaskCard taskCard;


    private BigDecimal ratePerHour;

    private BigDecimal totalTaskAmount;


}
