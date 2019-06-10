package us.redshift.timesheet.domain.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;

@Entity
@Table(name = "pss_locations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @CreatedBy
    @Column(updatable = false, nullable = false)
    @JsonIgnoreProperties(value = "createdBy", allowGetters = true)
    private String createdBy;


    @LastModifiedBy
    @Column(nullable = false)
    @JsonIgnoreProperties(value = "updatedBy", allowGetters = true)
    private String updatedBy;

    @Column(nullable = false, unique = true)
    private String location;
}
