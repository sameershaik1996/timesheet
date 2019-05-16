package us.redshift.timesheet.domain.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import us.redshift.timesheet.domain.common.BaseEntity;
import us.redshift.timesheet.domain.common.Address;

import javax.persistence.*;
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

    @Column(nullable = false, unique = true)
    private String clientCode;

    @Column(nullable = false)
    private String name;

    @JsonIgnoreProperties(value = "poc")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client")
    private Set<Poc> pocs = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private ClientStatus status;
    private String url;

    @ManyToOne()
    @JoinColumn(name = "industry_id", nullable = false)
    private Industry industry;


    @JsonIgnoreProperties(value = "clients")
    @ManyToMany()
    @JoinTable(name = "pss_clients_focus_area",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "focus_area_id"))
    private Set<FocusArea> focusAreas;


    private String futureFocus;
    private String about;
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "address_id")
    private Address address;
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "billing_address_id")
    private Address billingAddress;

    public void addPoc(Poc poc) {
        poc.setClient(this);
        pocs.add(poc);
    }

    public void setStatus(ClientStatus status) {
        if (status == null)
            status = ClientStatus.ACTIVE;
        this.status = status;
    }

    public void setFocusAreas(Set<FocusArea> focusAreas) {
        if (focusAreas == null)
            this.focusAreas = new HashSet<>();
        this.focusAreas = focusAreas;
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
