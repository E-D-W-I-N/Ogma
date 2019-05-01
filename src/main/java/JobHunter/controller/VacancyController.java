package JobHunter.controller;

import JobHunter.domain.Department;
import JobHunter.service.DepartmentService;
import JobHunter.service.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/vacancy")
public class VacancyController {

	private final VacancyService vacancyService;
	private final DepartmentService departmentService;

	@Autowired
	public VacancyController(VacancyService vacancyService,
							 DepartmentService departmentService) {
		this.vacancyService = vacancyService;
		this.departmentService = departmentService;
	}

	@GetMapping
	public String vacancies(Model model) {
		List<Department> departmentList = departmentService.findAllDepartments();

		model.addAttribute("departments", departmentList);
		return "vacancies";
	}

	@PostMapping("/add")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'HEADHUNTER')")
	public String add(@RequestParam("departmentId") Department department,
					  @RequestParam String vacancyName,
					  @RequestParam String description,
					  @RequestParam Float salary) {


		vacancyService.addVacancy(department, vacancyName, description, salary);
		return "redirect:/vacancy";
	}
}
