package us.redshift.timesheet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = "tasks")
public class Project extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal estimatedHour;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal estimatedCost;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectStatus status = ProjectStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectType type = ProjectType.FIXED;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Temporal(TemporalType.DATE)
    private Date startedOn;

    @Temporal(TemporalType.DATE)
    private Date endedOn;

    @ElementCollection(targetClass = Long.class)
    @JoinTable(name = "projects_employee")
    private Set<Long> employeeId = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    @JsonIgnoreProperties(value = {"projects"})
    private Client client;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "rate_card_id")
    @JsonIgnoreProperties("project")
    private RateCard rateCard;

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
