package Ogma.util;

import Ogma.domain.dto.CaptchaResponseDto;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ValidateCaptchaAndPasswordConfirm {

	private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

	public static Map<String, String> checkErrors(String captchaResponse, String secret, String passwordConfirm, String password,
												  BindingResult bindingResult, RestTemplate restTemplate) {
		Map<String, String> resultMap = new HashMap<>();
		String url = String.format(CAPTCHA_URL, secret, captchaResponse);
		CaptchaResponseDto response = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);

		boolean passwordIsEmpty = StringUtils.isEmpty(passwordConfirm);
		boolean passwordIsDifferent = password != null && !password.equals(passwordConfirm);

		assert password != null;
		if (!password.isEmpty()) {
			if (passwordIsEmpty)
                resultMap.put("password2Error", "Повторный пароль не может быть пустым");

			if (passwordIsDifferent)
                resultMap.put("passwordError", "Пароли отличаются");
		}

		assert response != null;
		if (!response.isSuccess())
            resultMap.put("captchaError", "Похоже вы все таки робот");

		if (passwordIsEmpty || passwordIsDifferent || !response.isSuccess() || bindingResult.hasErrors())
			resultMap.putAll(getErrors(bindingResult));

		return resultMap;
	}

	public static Map<String, String> getErrors(BindingResult bindingResult) {
		Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
				fieldError -> fieldError.getField() + "Error",
				FieldError::getDefaultMessage
		);
		return bindingResult.getFieldErrors().stream().collect(collector);
	}
}
