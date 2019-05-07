package us.redshift.timesheet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
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
    @JsonIgnoreProperties(value = "createdAt", allowGetters = true)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    @JsonIgnoreProperties(value = "updatedAt")
    private Instant updatedAt;


    public BaseEntity(Long id) {
        this.id = id;
    }
}
