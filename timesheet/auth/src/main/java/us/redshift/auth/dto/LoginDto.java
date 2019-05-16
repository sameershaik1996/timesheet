package us.redshift.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class LoginDto {

    @JsonProperty("username_email")
    @NotBlank
    private String userNameOrEmail;

    @NotBlank
    private String password;
}
