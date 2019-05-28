package us.redshift.timesheet.domain.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import us.redshift.timesheet.domain.common.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;

@Entity
@Table(name = "pss_pocs", uniqueConstraints = @UniqueConstraint(columnNames = {"client_id", "phoneNumber"}))
@Getter
@Setter
@NoArgsConstructor
public class Poc extends BaseEntity implements Serializable {

    private String name;
    private String phoneNumber;
    @Email
    private String email;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "client_id", nullable = false)
    @JsonIgnoreProperties(value = {"pocs"}, allowSetters = true)
    private Client client;
}
