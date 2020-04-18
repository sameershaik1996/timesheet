package us.redshift.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import us.redshift.auth.domain.PasswordPolicy;
import us.redshift.auth.repository.PasswordPolicyRepository;
import us.redshift.auth.repository.UserRepository;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("auth/v1/api/")
public class ValidationController {
    @Autowired
    private PasswordPolicyRepository passwordPolicyRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("usernameexists/{username}")
    public ResponseEntity<?> checkIfUserNameExists(@PathVariable String username) {
        return new ResponseEntity<>(userRepository.existsByUserName(username), HttpStatus.OK);

    }


    @GetMapping("passwordpolicy/{password}")
    public ResponseEntity<?> checkPassword(@PathVariable String password) {
        PasswordPolicy res = new PasswordPolicy();
        Pattern pattern;
        Matcher matcher;
        int flag = 1;
        List<PasswordPolicy> pp = passwordPolicyRepository.findAll();
        for (PasswordPolicy p : pp) {
            pattern = Pattern.compile(p.getPattern());
            matcher = pattern.matcher(password);
            if (!matcher.matches()) {
                res = p;
                flag = 1;
                break;
            }
        }
        if (flag == 1)
            return new ResponseEntity<>(res.getMessage(), HttpStatus.OK);
        else
            return new ResponseEntity<>(true, HttpStatus.OK);

    }
}
