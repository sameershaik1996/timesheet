package us.redshift.timesheet.domain.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import us.redshift.timesheet.domain.common.Address;
import us.redshift.timesheet.domain.common.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "pss_clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Client extends BaseEntity implements Serializable {

    @Column(nullable = false, unique = true)
    private String clientCode;

    @Column(nullable = false)
    private String name;

    @JsonIgnoreProperties(value = "client")
    @OneToMany(mappedBy = "client", cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.DETACH})
    private List<Poc> pocs;

    @Enumerated(EnumType.STRING)
    private ClientStatus status;
    private String url;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.DETACH})
    @JoinColumn(name = "industry_id", nullable = false)
    private Industry industry;


    @JsonIgnoreProperties(value = "clients")
    @ManyToMany
    @JoinTable(name = "pss_clients_focus_area",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "focus_area_id"))
    private List<FocusArea> focusAreas = new ArrayList<>();


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
        this.status = status == null ? ClientStatus.ACTIVE : status;
    }

    public void setFocusAreas(List<FocusArea> focusAreas) {
        this.focusAreas = focusAreas == null ? new ArrayList<>() : focusAreas;
    }

    public void setPocs(List<Poc> pocs) {
        this.pocs = pocs == null ? new ArrayList<>() : pocs;
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
