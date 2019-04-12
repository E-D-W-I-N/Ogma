package JobHunter.controller;

import JobHunter.domain.Department;
import JobHunter.domain.User;
import JobHunter.service.ApplicationService;
import JobHunter.service.DepartmentService;
import JobHunter.service.UserService;
import JobHunter.service.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/vacancy")
public class VacancyController {

	private final VacancyService vacancyService;
	private final ApplicationService applicationService;
	private final DepartmentService departmentService;
	private final UserService userService;

	@Autowired
	public VacancyController(VacancyService vacancyService,
							 ApplicationService applicationService,
							 DepartmentService departmentService,
							 UserService userService) {
		this.vacancyService = vacancyService;
		this.applicationService = applicationService;
		this.departmentService = departmentService;
		this.userService = userService;
	}

	@GetMapping
	public String vacancies(Model model) {
		List<Department> departmentList = departmentService.findAllDepartments();

		model.addAttribute("departments", departmentList);
		return "vacancies";
	}

	@PostMapping("/add")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'HEADHUNTER')")
	public String add(@RequestParam Long departmentId,
					  @RequestParam String vacancyName,
					  @RequestParam String description,
					  @RequestParam Float salary) {


		vacancyService.addVacancy(departmentId, vacancyName, description, salary);
		return "redirect:/vacancy";
	}

	@PostMapping
	public String addApplication(@AuthenticationPrincipal User user,
								 @RequestParam Long vacancyId,
								 Model model) {

		if (!userService.profileIsFilled(user)) {
			model.addAttribute("message", "You must fill your profile before creating applications");
			List<Department> departmentList = departmentService.findAllDepartments();

			model.addAttribute("departments", departmentList);
			return "vacancies";
		}

		if (!applicationService.addApplication(vacancyId, user, LocalDate.now())) {
			model.addAttribute("message", "You already create application for this vacancy");
			List<Department> departmentList = departmentService.findAllDepartments();

			model.addAttribute("departments", departmentList);
			return "vacancies";
		}

		return "redirect:/applications";
	}
}
