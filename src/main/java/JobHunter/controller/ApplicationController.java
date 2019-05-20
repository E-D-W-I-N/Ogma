package JobHunter.controller;

import JobHunter.domain.*;
import JobHunter.service.ApplicationService;
import JobHunter.service.DepartmentService;
import JobHunter.service.UserService;
import JobHunter.util.PDFGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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

	@GetMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> applicationsReport(@AuthenticationPrincipal User user) {
		List<Application> applications = new ArrayList<>();
		if (user.getRoles().contains(Role.ADMIN)) {
			applications = applicationService.findAllApplications();
		} else if (user.getRoles().contains(Role.HEADHUNTER)) {
			Department department = departmentService.findDepartmentByUser(user);
			applications = applicationService.findApplicationsByDepartment(department);
		} else
			applications = applicationService.findApplicationsByUser(user);

		String title = "Заявления";
		List<String> tableHeader = Arrays.asList("ID", "Вакансия", "Отдел", "Кандидат", "Дата", "Время");
		List content = new ArrayList();

		for (Application application : applications) {
			content.add(Arrays.asList(application.getId().toString(), application.getVacancy().getVacancyName(),
					application.getDepartment().getDepartmentName(), application.getUser().getUsername(),
					application.getDateTime().toLocalDate().toString(), application.getDateTime().toLocalTime().toString().substring(0, 8)));
		}


		ByteArrayInputStream bis = PDFGenerator.customerPDFReport(title, tableHeader, content);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=applications.pdf");

		return ResponseEntity
				.ok()
				.headers(headers)
				.contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
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
