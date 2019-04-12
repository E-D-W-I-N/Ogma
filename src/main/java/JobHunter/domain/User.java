package JobHunter.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Set;

@Entity
@Table
@Data
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long id;

	@NotBlank(message = "Username can't be empty")
	private String username;
	@NotBlank(message = "Password can't be empty")
	private String password;

	private String firstName;
	private String lastName;
	private boolean active;
	private String activationCode;
	private Float experience;
	private String education;

	@Email(message = "Email is not correct")
	@NotBlank(message = "Please fill the Email address")
	private String email;
	private String phone;

	@ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
	@Enumerated(EnumType.STRING)
	private Set<Role> roles;

	@JoinColumn
	@OneToOne(cascade = CascadeType.ALL)
	private HeadHunter headHunter;

//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	private Set<Application> applications;

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
}
