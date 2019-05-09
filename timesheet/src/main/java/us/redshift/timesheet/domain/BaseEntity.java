package us.redshift.timesheet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;


@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    @JsonIgnoreProperties(value = "createdTimeStamp", allowGetters = true)
    private Instant createdTimeStamp;

    @LastModifiedDate
    @Column(nullable = false)
    @JsonIgnoreProperties(value = "updatedTimeStamp", allowGetters = true)
    private Instant updatedTimeStamp;


    @CreatedBy
    @Column(updatable = false, nullable = false)
    @JsonIgnoreProperties(value = "createdBy", allowGetters = true)
    private Long createdBy;


    @LastModifiedBy
    @Column(nullable = false)
    @JsonIgnoreProperties(value = "updatedBy", allowGetters = true)
    private Long updatedBy;

}
