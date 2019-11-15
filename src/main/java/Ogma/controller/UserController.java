package Ogma.controller;

import Ogma.domain.Role;
import Ogma.domain.User;
import Ogma.domain.dto.UserDto;
import Ogma.service.impl.UserServiceImpl;
import Ogma.util.PDFGenerator;
import Ogma.util.ValidateCaptchaAndPasswordConfirm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserServiceImpl userServiceImpl;
	private final RestTemplate restTemplate;

	@Value("${recaptcha.secret}")
	private String secret;

	@Autowired
    public UserController(UserServiceImpl userServiceImpl, RestTemplate restTemplate) {
        this.userServiceImpl = userServiceImpl;
		this.restTemplate = restTemplate;
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping
	public String userList(Model model) {
        model.addAttribute("users", userServiceImpl.findUsersByRoles(Role.USER));

		return "userList";
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> usersReport() {
        List<User> users = userServiceImpl.findUsersByRoles(Role.USER);
		String title = "Пользователи";
        List<String> tableHeader = Arrays.asList("ID", "Логин", "Имя", "Фамилия", "Email", "Телефон");
		List content = new ArrayList();
		for (User user : users) {
			content.add(Arrays.asList(user.getId().toString(), user.getUsername(), user.getFirstName(), user.getLastName(),
                    user.getEmail(), user.getPhone()));
		}


		ByteArrayInputStream bis = PDFGenerator.customerPDFReport(title, tableHeader, content);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=users.pdf");

		return ResponseEntity
				.ok()
				.headers(headers)
				.contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
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

        userServiceImpl.saveUser(user, username, form);

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

        userServiceImpl.updateProfile(user, userDto.getUsername(), password, userDto.getEmail(), userDto.getFirstName(),
                userDto.getLastName(), userDto.getPhone());

		return "redirect:/user/profile";
	}
}
