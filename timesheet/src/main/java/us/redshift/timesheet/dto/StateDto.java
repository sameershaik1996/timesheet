package us.redshift.timesheet.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StateDto {

    private Long id;
    private String name;
    private CountryDto country;
}
