package us.redshift.timesheet.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {

    private Long id;
    private String addressLine1;
    private String addressLine2;
    private CountryDto country;
    private StateDto state;
    private String zipCode;

}
