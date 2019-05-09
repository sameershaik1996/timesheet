package us.redshift.timesheet.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "pss_states")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "country_id")
    private Country country;
}
