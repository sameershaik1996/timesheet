package us.redshift.authutility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import us.redshift.authutility.model.User;
import us.redshift.authutility.security.CurrentUser;
import us.redshift.authutility.security.JwtTokenProvider;
import us.redshift.authutility.security.UserPrincipal;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TestController {
    @Autowired
    JwtTokenProvider provider;
    @RequestMapping("/api/test")
    public void Test(@CurrentUser UserPrincipal principal, HttpServletRequest req){
        User user= (User)req.getAttribute("details");
        System.out.println(principal.getUsername());

    }
}
