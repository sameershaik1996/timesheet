package us.redshift.timesheet.domain.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import us.redshift.timesheet.domain.common.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pss_focus_areas", uniqueConstraints = @UniqueConstraint(columnNames = {"code", "name"}))
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
    @ManyToMany(mappedBy = "focusAreas")
    private List<Client> clients = new ArrayList<>();
}
