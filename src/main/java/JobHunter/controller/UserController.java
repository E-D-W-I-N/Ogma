package JobHunter.controller;

import JobHunter.domain.Role;
import JobHunter.domain.User;
import JobHunter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping
	public String userList(Model model) {
		model.addAttribute("users", userService.findUsersByRoles(Role.USER));

		return "userList";
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("{user}")
	public String userEditForm(@PathVariable User user, Model model) {

		model.addAttribute("user", user);
		model.addAttribute("roles", Role.values());

		return "userEdit";
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping
	public String userSave(
			@RequestParam String username,
			@RequestParam Map<String, String> form,
			@RequestParam("userId") User user
	) {

		userService.saveUser(user, username, form);

		return "redirect:/user";
	}

	@GetMapping("profile")
	public String getProfile(Model model, @AuthenticationPrincipal User user) {
		model.addAttribute("user", user);

		return "profile";
	}

	@PostMapping("profile")
	public String updateProfile(
			@AuthenticationPrincipal User user,
			@RequestParam String username,
			@RequestParam String password,
			@RequestParam String email,
			@RequestParam String firstName,
			@RequestParam String lastName,
			@RequestParam String education,
			@RequestParam Float experience,
			@RequestParam String phone
	) {
		userService.updateProfile(user, username, password, email, firstName, lastName, education, experience, phone);

		return "redirect:/user/profile";
	}
}
