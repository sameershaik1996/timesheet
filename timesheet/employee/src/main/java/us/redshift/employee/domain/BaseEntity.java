package us.redshift.employee.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.time.Instant;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @CreatedDate
    @JsonIgnoreProperties(allowGetters = true)
    @Column(nullable = false, updatable = false)
    private Instant createdTimeStamp;

    @JsonIgnoreProperties(allowGetters = true)
    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedTimeStamp;

    @CreatedBy
    @Column(nullable = true,updatable = false)
    private Long createdBy;


    @LastModifiedBy
    @Column(nullable = true)
    private Long updatedBy;



}
