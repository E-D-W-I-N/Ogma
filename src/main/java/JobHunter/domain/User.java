package JobHunter.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Username can't be empty")
	private String username;

	@NotBlank(message = "Password can't be empty")
	private String password;

	@NotBlank(message = "First Name can't be empty")
	private String firstName;

	@NotBlank(message = "Last Name can't be empty")
	private String lastName;

	private boolean active;
	private String activationCode;
	private String experience;
	private String education;

	@Email(message = "Email is not correct")
	@NotBlank(message = "Please fill the Email address")
	private String email;

	@Pattern(regexp = "\\+\\d(-\\d{3}){2}-\\d{4}",
			message = "Please, fill your phone number using right number format. For example: +7-903-503-9832")
	private String phone;

	@ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
	@Enumerated(EnumType.STRING)
	private Set<Role> roles;

	@ManyToOne
	@JoinColumn
	private Department department;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getRoles();
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
		return isActive();
	}

	public boolean isAdmin() {
		return roles.contains(Role.ADMIN);
	}

	public boolean isHeadHunter() {
		return roles.contains(Role.HEADHUNTER);
	}
}
