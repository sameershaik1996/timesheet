package us.redshift.timesheet.domain.project;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import us.redshift.timesheet.domain.client.Client;
import us.redshift.timesheet.domain.common.BaseEntity;
import us.redshift.timesheet.domain.ratecard.RateCard;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "pss_projects")
@Getter
@Setter
@NoArgsConstructor
public class Project extends BaseEntity implements Serializable {

    @Column(nullable = false, unique = true)
    private String projectCode;

    @Column(nullable = false)
    private String name;


    private String description;

    @Column(nullable = false)
    private Long estimatedDays;

    @Column(precision = 10, scale = 2)
    private BigDecimal estimatedCost;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectStatus status = ProjectStatus.ACTIVE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectType type;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Temporal(TemporalType.DATE)
    private Date startedOn;

    @Temporal(TemporalType.DATE)
    private Date endedOn;

    @Column(nullable = false)
    @ElementCollection(targetClass = Long.class)
    @JoinTable(name = "pss_project_employees")
    private List<Long> employeeId = new ArrayList<>();

    @Column(nullable = false)
    private Long managerId;

    @ManyToOne()
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "rate_card_id")
    private RateCard rateCard;


}
