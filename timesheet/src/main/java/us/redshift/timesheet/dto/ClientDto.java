package us.redshift.timesheet.dto;

import lombok.*;
import us.redshift.timesheet.domain.ClientStatus;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClientDto extends BaseDto {


    private List<PocDto> pocs;
    private String url;
    private ClientStatus status;
    private String domain;
    private String specialization;
    private String offering;
    private String about;
    private String address1;
    private String address2;
    private String state;
    private String country;
    private String zipCode;
}
