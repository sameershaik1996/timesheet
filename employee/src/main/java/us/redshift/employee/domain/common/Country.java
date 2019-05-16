package us.redshift.employee.domain.common;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.Generated;
import javax.persistence.*;

@Entity
@Table(name="emp_countries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String countryCode;

    private String name;

    private int phoneCode;
}
