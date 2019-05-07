package us.redshift.timesheet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "pss_pocs")
@Getter
@Setter
@NoArgsConstructor
public class Poc extends BaseEntity {

    private String name;
    private String phoneNumber;
    private String email;


    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "client_id", nullable = false)
    @JsonIgnoreProperties(value = {"pocs"}, allowSetters = true)
    private Client client;
}
