package us.redshift.timesheet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="pss_invoices")
public class Invoice extends BaseEntity implements Serializable {



    @ManyToOne
    @JoinColumn(name = "project_id",nullable = false)
    @JsonIgnoreProperties({"client","rateCard"})
    private Project project;

    @ManyToOne
    @JoinColumn(name = "client_id",nullable = false)
    @JsonIgnoreProperties({"project"})
    private Client client;



    @OneToMany(cascade = CascadeType.ALL, mappedBy = "invoice")
    @JsonIgnoreProperties({"invoice"})
    private List<InvoiceDetail> invoiceDetails = new ArrayList<>();

    @Temporal(TemporalType.DATE)
    private Date fromDate;

    @Temporal(TemporalType.DATE)
    private Date toDate;

    @Temporal(TemporalType.DATE)
    private Date sentToClientOn;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus status=InvoiceStatus.Pending;


    private Long ownerId;

    public void addInvoiceDetails(InvoiceDetail invoiceDetail) {

        invoiceDetail.setInvoice(this);
        invoiceDetails.add(invoiceDetail);
    }


}
