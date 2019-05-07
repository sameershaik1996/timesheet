package us.redshift.timesheet.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;


@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = "projects")
public class Client extends BaseEntity {


    @Column(nullable = false)
    @Size(max = 32)
    private String name;

    @Column(nullable = false, unique = true)
    @Size(max = 64)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClientStatus status = ClientStatus.ACTIVE;

    @Column(nullable = false, unique = true)
    private Integer phoneNumber;
    private String domain;
    private String specialization;
    private String offering;
    private String about;
    private String address;

    private Long countryCode;
    private Long stateCode;

//    //    @JsonManagedReference
//    @JsonIgnoreProperties(value = "client")
//    @OneToMany(mappedBy = "client",
//            cascade = CascadeType.ALL)
//    private List<Project> projects = new ArrayList<>();
//
//    public void add(Project project) {
//        if (project == null)
//            projects = new ArrayList<>();
//        project.setClient(this);
//        projects.add(project);
//    }

}
