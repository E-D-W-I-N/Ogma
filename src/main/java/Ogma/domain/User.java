package Ogma.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.Set;

@Entity
@Data
@Table(name = "usr")
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Логин не может быть пустым")
	private String username;

	@NotBlank(message = "Пароль не может быть пустым")
	private String password;

	@NotBlank(message = "Имя не может быть пустым")
	private String firstName;

	@NotBlank(message = "Фамилия не может быть пустым")
	private String lastName;

	private boolean active;
	private String activationCode;

	@Email(message = "Неверный формат")
	@NotBlank(message = "Пожалуйста, введите Email адрес")
	private String email;

	@Pattern(regexp = "\\+\\d(-\\d{3}){2}-\\d{4}",
			message = "Пожалуйста, введите номер используя правильный формат: +7-XXX-XXX-XXXX")
	private String phone;

	@ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
	@Enumerated(EnumType.STRING)
	private Set<Role> roles;

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

	public boolean isModerator() {
		return roles.contains(Role.MODERATOR);
	}
}
