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

    private Long id;
    @NonNull
    private String clientCode;
    @NonNull
    private String name;
    private List<PocDto> pocs;
    private String url;
    private ClientStatus status;
    private IndustryDto industry;
    private List<FocusAreaDto> focusAreas;
    private String futureFocus;
    private String about;
    private Boolean addressFlag;
    private AddressDto address;
    private AddressDto billingAddress;


}
