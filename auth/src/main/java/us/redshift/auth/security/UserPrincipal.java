package us.redshift.auth.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import us.redshift.auth.domain.Permission;
import us.redshift.auth.domain.Role;
import us.redshift.auth.domain.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Getter
@Setter

@NoArgsConstructor
public class UserPrincipal implements UserDetails {


    private Long id;


    private String username;

    private Long employeeId;

    @JsonIgnore
    private String email;

    @JsonIgnore
    private String password;


    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(Long id, Long employeeId, String username, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.employeeId = employeeId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserDetails create(User user) {
        /*Set<Permission>  permissions=new HashSet<Permission>();
        for ( Role r:user.getRoles())
        {
            permissions=r.getPermissions();
        }
        List<GrantedAuthority> authorities = permissions.stream().map(permission ->
                new SimpleGrantedAuthority(permission.getName().name())
        ).collect(Collectors.toList());
        */
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getName().toString()));
        return new UserPrincipal(
                user.getId(),
                user.getEmployeeId(),
                user.getUserName(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(
            Collection<Role> roles) {

        return getGrantedAuthorities(getPrivileges(roles));
    }

    private static List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

    private static List<String> getPrivileges(Collection<Role> roles) {

        List<String> permissions = new ArrayList<>();
        List<Permission> collection = new ArrayList<>();
        for (Role role : roles) {
            collection.addAll(role.getPermissions());
        }
        for (Permission item : collection) {
            permissions.add(item.getName().toString());
        }
        return permissions;
    }

    public Long getId() {
        return id;
    }


    public String getEmail() {
        return email;
    }

    public Long getEmployyeId() {
        return employeeId;
    }


    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
