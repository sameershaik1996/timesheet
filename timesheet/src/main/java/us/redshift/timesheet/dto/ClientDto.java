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

    @NonNull
    private String clientCode;
    @NonNull
    private String name;
    private List<PocDto> pocs;
    private String url;
    private ClientStatus status;
    private String domain;
    private String specialization;
    private String offering;
    private String about;
    private Boolean addressFlag;
    private AddressDto address;
    private AddressDto billingAddress;


}
