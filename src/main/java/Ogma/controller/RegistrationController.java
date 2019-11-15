package Ogma.controller;

import Ogma.domain.User;
import Ogma.service.impl.UserServiceImpl;
import Ogma.util.ValidateCaptchaAndPasswordConfirm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class RegistrationController {

    private final UserServiceImpl userServiceImpl;
	private final RestTemplate restTemplate;

	@Value("${recaptcha.secret}")
	private String secret;

	@Autowired
    public RegistrationController(UserServiceImpl userServiceImpl, RestTemplate restTemplate) {
        this.userServiceImpl = userServiceImpl;
		this.restTemplate = restTemplate;
	}

	@GetMapping("/registration")
	public String registration() {
		return "registration";
	}

	@PostMapping("/registration")
	public String addUser(
			@RequestParam("password2") String passwordConfirm,
			@RequestParam("g-recaptcha-response") String captchaResponse,
			@Valid User user,
			BindingResult bindingResult,
			Model model) {

		Map<String, String> resultMap = ValidateCaptchaAndPasswordConfirm.checkErrors(captchaResponse, secret, passwordConfirm,
				user.getPassword(), bindingResult, restTemplate);

		if (!resultMap.isEmpty()) {
			model.addAllAttributes(resultMap);
			return "registration";
		}
        if (!userServiceImpl.addUser(user)) {
			model.addAttribute("usernameError", "Этот логин уже занят");
			return "registration";
		}

		model.addAttribute("messageType", "success");
		model.addAttribute("message", "На указанную почту была выслана ссылка для активации аккаунта");
		return "registration";
	}

	@GetMapping("/activate/{code}")
	public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userServiceImpl.activateUser(code);

		if (isActivated) {
			model.addAttribute("messageType", "success");
			model.addAttribute("message", "Пользователь был успешно активирован");
		} else {
			model.addAttribute("messageType", "danger");
			model.addAttribute("message", "Код активации не найден");
		}

		return "login";
	}
}
