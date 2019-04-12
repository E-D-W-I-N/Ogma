package JobHunter.controller;

import JobHunter.domain.Application;
import JobHunter.domain.Role;
import JobHunter.domain.User;
import JobHunter.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/applications")
public class ApplicationController {

	private final ApplicationService applicationService;

	@Autowired
	public ApplicationController(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}

	@GetMapping
	public String applicationsForUser(@AuthenticationPrincipal User user,
									  Model model) {

		if (user.getRoles().contains(Role.ADMIN) || user.getRoles().contains(Role.HEADHUNTER)) {
			List<Application> applications = applicationService.findAllApplications();

			model.addAttribute("applications", applications);
			return "applications";
		}

		List<Application> applications = applicationService.findApplicationsByUser(user);

		model.addAttribute("applications", applications);
		return "applications";
	}
}
