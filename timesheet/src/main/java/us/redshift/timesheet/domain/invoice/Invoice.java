package us.redshift.timesheet.domain.invoice;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import us.redshift.timesheet.domain.client.Client;
import us.redshift.timesheet.domain.common.BaseEntity;
import us.redshift.timesheet.domain.project.Project;
import us.redshift.timesheet.domain.taskcard.TaskCardDetail;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pss_invoices")
public class Invoice extends BaseEntity implements Serializable {

    @ManyToOne()
    @JoinColumn(name = "client_id", nullable = false)
    @JsonIgnoreProperties({"pocs","industry","focusAreas","address","billingAddress"})
    private Client client;


    @ManyToOne()
    @JoinColumn(name = "project_id", nullable = false)
    @JsonIgnoreProperties({"client","rateCard"})
    private Project project;

    @Column(name = "from_date")
    @Temporal(TemporalType.DATE)
    private Date fromDate;
    @Column(name = "to_date")
    @Temporal(TemporalType.DATE)
    private Date toDate;

    @Temporal(TemporalType.DATE)
    private Date sentToClient   ;

    private BigDecimal totalHours;

    private BigDecimal totalAmount;

    @JsonIgnoreProperties(value="employees")
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "pss_invoice_task_card_details",
            joinColumns = @JoinColumn(name = "invoice_id"),
            inverseJoinColumns = @JoinColumn(name = "task_card_detail_id"))
    private Set<TaskCardDetail> taskCardDetails = new HashSet<>();



    @OneToMany(mappedBy = "invoice",
            cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = "invoice")
    private Set<InvoiceDetails> invoiceDetails = new HashSet<>();


    public void addInvoiceDetail(InvoiceDetails invoiceDetail) {
        this.invoiceDetails.add(invoiceDetail);
        invoiceDetail.setInvoice(this);
    }

}
