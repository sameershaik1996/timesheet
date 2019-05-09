package us.redshift.timesheet.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pss_projects")
@Getter
@Setter
@NoArgsConstructor
public class Project extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String projectCode;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Long estimatedDays;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal estimatedCost;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectStatus status;

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
    private Set<Long> employeeId = new HashSet<>();

    @Column(nullable = false)
    private Long managerId;

    @ManyToOne()
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "rate_card_id")
    private RateCard rateCard;

    public void setStatus(ProjectStatus status) {
        if (status == null)
            status = ProjectStatus.INACTIVE;
        this.status = status;
    }

    public void setType(ProjectType type) {
        if (type == null)
            type = ProjectType.FIXED_BID;
        this.type = type;
    }


    //    @JsonIgnoreProperties("project")
//    @OneToMany(mappedBy = "project",
//            cascade = CascadeType.ALL)
//    private List<Task> tasks = new ArrayList<>();


//    public void add(Task task) {
//        if (task == null)
//            tasks = new ArrayList<>();
//        task.setProject(this);
//        tasks.add(task);
//    }


}
