package JobHunter.service;

import JobHunter.domain.Department;
import JobHunter.domain.Role;
import JobHunter.domain.User;
import JobHunter.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

	private final UserRepo userRepo;
	private final MailSender mailSender;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserService(UserRepo userRepo, MailSender mailSender,
					   PasswordEncoder passwordEncoder) {
		this.userRepo = userRepo;
		this.mailSender = mailSender;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findUserByUsername(username);

		if (user == null)
			throw new UsernameNotFoundException("Пользователь не найден");

		return user;
	}

	public boolean addUser(User user) {
		User userFromDb = userRepo.findUserByUsername(user.getUsername());

		if (userFromDb != null)
			return false;

		user.setRoles(Collections.singleton(Role.USER));
		user.setActivationCode(UUID.randomUUID().toString());
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		userRepo.save(user);

		sendMessage(user);

		return true;
	}

	private void sendMessage(User user) {
		if (!StringUtils.isEmpty(user.getEmail())) {
			String message = String.format(
					"Hello, %s! \n" +
							"Weclome to JobHunter. Please visit next link: http://localhost:8080/activate/%s",
					user.getUsername(),
					user.getActivationCode()
			);

			mailSender.send(user.getEmail(), "Activation code", message);
		}
	}

	public boolean activateUser(String code) {
		User user = userRepo.findByActivationCode(code);

		if (user == null)
			return false;

		user.setActivationCode(null);
		user.setActive(true);
		userRepo.save(user);

		return true;
	}

	public void saveUser(User user, String username, Map<String, String> form, Department department) {
		user.setUsername(username);

		Set<String> roles = Arrays.stream(Role.values())
				.map(Role::name)
				.collect(Collectors.toSet());

		user.getRoles().clear();

		for (String key : form.keySet()) {
			if (roles.contains(key)) {
				user.getRoles().add(Role.valueOf(key));
				if (Role.valueOf(key) == Role.HEADHUNTER)
					user.setDepartment(department);
			}
		}

		userRepo.save(user);
	}

	public void updateProfile(User user, String username, String password, String email, String firstName,
							  String lastName, String education, String experience, String phone) {

		if (!StringUtils.isEmpty(password))
			user.setPassword(passwordEncoder.encode(password));

		if (!StringUtils.isEmpty(username))
			user.setUsername(username);

		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEducation(education);
		user.setExperience(experience);
		user.setPhone(phone);

		String userEmail = user.getEmail();
		boolean isEmailChanged = (email != null && !StringUtils.isEmpty(email) && !email.equals(userEmail)) ||
				(userEmail != null && !StringUtils.isEmpty(userEmail) && !userEmail.equals(email));

		if (isEmailChanged) {
			user.setEmail(email);
			user.setActivationCode(UUID.randomUUID().toString());
			user.setActive(false);
			sendMessage(user);
		}

		userRepo.save(user);

	}

	public boolean profileIsFilled(User user) {
		return user.getFirstName() != null && !user.getFirstName().isEmpty() &&
				user.getLastName() != null && !user.getLastName().isEmpty() &&
				user.getPhone() != null && !user.getPhone().isEmpty();
	}

	public List<User> findUsersByRoles(Role role) {
		return userRepo.findUsersByRoles(role);
	}
}
