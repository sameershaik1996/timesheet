package us.redshift.timesheet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "pss_clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Client extends BaseEntity {


    @Size(max = 32)
    private String name;

    @JsonIgnoreProperties(value = "poc")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client")
    private Set<Poc> pocs = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private ClientStatus status;

    private String url;
    private String domain;
    private String specialization;
    private String offering;
    private String about;
    private String address1;
    private String address2;
    private String state;
    private String Country;
    private String zipCode;

    public Client(Long id) {
        super(id);
    }


    public void setStatus(ClientStatus status) {
        if (status == null)
            status = ClientStatus.ACTIVE;
        this.status = status;
    }

    public void addPoc(Poc poc) {
        poc.setClient(this);
        pocs.add(poc);
    }


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
