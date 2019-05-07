package us.redshift.employee.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter

public class SignUpDto implements Serializable {

    @JsonProperty(value = "username")
    private String userName;

    private String password;

    @JsonProperty("employee_id")
    private Long employeeId;

    @JsonProperty("email")
    private String emailId;


}
