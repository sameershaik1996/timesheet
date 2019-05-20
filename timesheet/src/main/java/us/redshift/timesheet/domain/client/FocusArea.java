package us.redshift.timesheet.domain.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import us.redshift.timesheet.domain.common.BaseEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pss_focus_areas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "clients")
@ToString
public class FocusArea extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String code;
    @Column(nullable = false, unique = true)
    private String name;

    @JsonIgnoreProperties({"focusAreas"})
    @JsonIgnore
    @ManyToMany(mappedBy = "focusAreas", cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.DETACH})
    private Set<Client> clients = new HashSet<>();
}