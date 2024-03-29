package us.redshift.timesheet.domain.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.search.annotations.Field;

import org.hibernate.search.annotations.Indexed;
import us.redshift.timesheet.domain.common.Address;
import us.redshift.timesheet.domain.common.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    @JsonIgnoreProperties(value = "client")
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private Set<Poc> pocs;


    @Enumerated(EnumType.STRING)
    private ClientStatus status = ClientStatus.ACTIVE;
    private String url;

    @ManyToOne
    @JoinColumn(name = "industry_id")
    @JsonIgnoreProperties(value = "clients")
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

    private String notes;

    public void addPoc(Poc poc) {
        if (pocs == null)
            pocs = new HashSet<>();
        poc.setClient(this);
        pocs.add(poc);
    }


}
