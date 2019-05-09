package us.redshift.timesheet.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "pss_industries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Industry extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;
}
