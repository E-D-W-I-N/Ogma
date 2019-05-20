package JobHunter.controller;

import JobHunter.domain.Department;
import JobHunter.domain.Vacancy;
import JobHunter.service.DepartmentService;
import JobHunter.service.VacancyService;
import JobHunter.util.PDFGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
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

	@GetMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> vacanciesReport() {
		List<Vacancy> vacancies = vacancyService.findAllVacancies();

		String title = "Вакансии";
		List<String> tableHeader = Arrays.asList("ID", "Отдел", "Вакансия", "Описание", "Зарплата");
		List content = new ArrayList();

		for (Vacancy vacancy : vacancies) {
			content.add(Arrays.asList(vacancy.getId().toString(), vacancy.getDepartment().getDepartmentName(),
					vacancy.getVacancyName(), vacancy.getDescription(), vacancy.getSalary().toString()));
		}


		ByteArrayInputStream bis = PDFGenerator.customerPDFReport(title, tableHeader, content);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=vacancies.pdf");

		return ResponseEntity
				.ok()
				.headers(headers)
				.contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
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
