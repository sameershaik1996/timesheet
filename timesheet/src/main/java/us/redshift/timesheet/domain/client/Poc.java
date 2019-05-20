package us.redshift.timesheet.domain.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import us.redshift.timesheet.domain.common.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;

@Entity
@Table(name = "pss_pocs")
@Getter
@Setter
@NoArgsConstructor
public class Poc extends BaseEntity implements Serializable {

    private String name;
    private String phoneNumber;
    @Email
    private String email;


    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "client_id", nullable = false)
    @JsonIgnoreProperties(value = {"pocs"}, allowSetters = true)
    private Client client;
}
