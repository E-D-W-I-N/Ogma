package JobHunter.controller;

import JobHunter.domain.*;
import JobHunter.service.ApplicationService;
import JobHunter.service.DepartmentService;
import JobHunter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/applications")
public class ApplicationController {

	private final ApplicationService applicationService;
	private final UserService userService;
	private final DepartmentService departmentService;

	@Autowired
	public ApplicationController(ApplicationService applicationService, UserService userService, DepartmentService departmentService) {
		this.applicationService = applicationService;
		this.userService = userService;
		this.departmentService = departmentService;
	}

	@GetMapping
	public String applicationsForUser(@AuthenticationPrincipal User user,
									  Model model) {

		if (user.getRoles().contains(Role.ADMIN)) {
			List<Application> applications = applicationService.findAllApplications();

			model.addAttribute("applications", applications);
			return "applications";
		} else if (user.getRoles().contains(Role.HEADHUNTER)) {
			Department department = departmentService.findDepartmentByUser(user);
			List<Application> applications = applicationService.findApplicationsByDepartment(department);

			model.addAttribute("applications", applications);
			return "applications";
		}

		List<Application> applications = applicationService.findApplicationsByUser(user);

		model.addAttribute("applications", applications);
		return "applications";
	}

	@PostMapping("/delete")
	public String deleteApplication(@AuthenticationPrincipal User user,
									@RequestParam("applicationId") Application application,
									Model model) {

		if (applicationService.deleteApplication(application, user)) {
			model.addAttribute("messageType", "success");
			model.addAttribute("message", "Заявление было успешно удалено");
		} else {
			model.addAttribute("messageType", "danger");
			model.addAttribute("message", "Вы не можете удалить это заявление");
		}

		List<Application> applications = applicationService.findApplicationsByUser(user);

		model.addAttribute("applications", applications);
		return "applications";
	}

	@PostMapping("/add")
	public String addApplication(@AuthenticationPrincipal User user,
								 @RequestParam("vacancyId") Vacancy vacancy,
								 Model model) {

		if (!userService.profileIsFilled(user)) {
			model.addAttribute("message", "Вы должны заполнить профиль, прежде чем подавать заявления");
			List<Department> departmentList = departmentService.findAllDepartments();

			model.addAttribute("departments", departmentList);
			return "vacancies";
		}

		if (!applicationService.addApplication(vacancy, user, LocalDateTime.now())) {
			model.addAttribute("message", "Вы уже подавали заявление на эту вакансию");
			List<Department> departmentList = departmentService.findAllDepartments();

			model.addAttribute("departments", departmentList);
			return "vacancies";
		}

		return "redirect:/applications";
	}
}
