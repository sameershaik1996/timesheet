package us.redshift.timesheet.dto.common;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CountryDto {

    private Long id;
    private String countryCode;
    private String name;
    private int phoneCode;
}
