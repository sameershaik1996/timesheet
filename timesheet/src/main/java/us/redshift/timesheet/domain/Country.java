package us.redshift.timesheet.domain;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "pss_countries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String countryCode;

    private String name;

    private int phoneCode;
}
