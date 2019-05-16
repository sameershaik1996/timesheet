package us.redshift.timesheet.domain.common;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "pss_addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String addressLine1;

    private String addressLine2;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;

    private String zipCode;

}
