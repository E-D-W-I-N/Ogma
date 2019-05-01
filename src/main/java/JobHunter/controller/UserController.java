package JobHunter.controller;

import JobHunter.domain.Department;
import JobHunter.domain.Role;
import JobHunter.domain.User;
import JobHunter.domain.dto.UserDto;
import JobHunter.service.DepartmentService;
import JobHunter.service.UserService;
import JobHunter.util.ValidateCaptchaAndPasswordConfirm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

	private final UserService userService;
	private final DepartmentService departmentService;
	private final RestTemplate restTemplate;

	@Value("${recaptcha.secret}")
	private String secret;

	@Autowired
	public UserController(UserService userService, DepartmentService departmentService, RestTemplate restTemplate) {
		this.userService = userService;
		this.departmentService = departmentService;
		this.restTemplate = restTemplate;
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

		List<Department> departmentList = departmentService.findAllDepartments();

		model.addAttribute("user", user);
		model.addAttribute("roles", Role.values());
		model.addAttribute("departments", departmentList);

		return "userEdit";
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping
	public String userSave(
			@RequestParam String username,
			@RequestParam Map<String, String> form,
			@RequestParam(value = "departmentId", required = false) Department department,
			@RequestParam("userId") User user
	) {

		userService.saveUser(user, username, form, department);

		return "redirect:/user";
	}

	@GetMapping("profile")
	public String getProfile(Model model, @AuthenticationPrincipal User user) {
		model.addAttribute("user", user);

		return "profile";
	}

	@PostMapping("profile")
	public String updateProfile(
			@RequestParam("password") String password,
			@RequestParam("password2") String passwordConfirm,
			@RequestParam("g-recaptcha-response") String captchaResponse,
			@AuthenticationPrincipal User user,
			@Valid UserDto userDto,
			BindingResult bindingResult,
			Model model) {

		Map<String, String> resultMap = ValidateCaptchaAndPasswordConfirm.checkErrors(captchaResponse, secret, passwordConfirm,
				password, bindingResult, restTemplate);

		if (!resultMap.isEmpty()) {
			model.addAllAttributes(resultMap);
			model.addAttribute(user);
			return "profile";
		}

		userService.updateProfile(user, userDto.getUsername(), password, userDto.getEmail(), userDto.getFirstName(),
				userDto.getLastName(), userDto.getEducation(), userDto.getExperience(), userDto.getPhone());

		return "redirect:/user/profile";
	}
}
