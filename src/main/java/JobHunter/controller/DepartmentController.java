package JobHunter.controller;

import JobHunter.domain.Department;
import JobHunter.domain.User;
import JobHunter.domain.Vacancy;
import JobHunter.service.DepartmentService;
import JobHunter.util.PDFGenerator;
import JobHunter.util.ValidateCaptchaAndPasswordConfirm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/departments")
public class DepartmentController {

	private final DepartmentService departmentService;

	@Autowired
	public DepartmentController(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	@GetMapping
	public String departments(Model model) {
		Iterable<Department> departments = departmentService.findAllDepartments();

		model.addAttribute("departments", departments);
		return "departments";
	}

	@GetMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> departmentsReport() {
		List<Department> departments = departmentService.findAllDepartments();

		String title = "Отделы";
		List<String> tableHeader = Arrays.asList("ID", "Отдел", "Работники", "Вакансии");
		List content = new ArrayList();

		for (Department department : departments) {

			StringBuilder headHunters = new StringBuilder();
			StringBuilder vacancies = new StringBuilder();
			for (User headHunter : department.getHeadHunters()) {
				headHunters.append(headHunter.getUsername()).append(".").append(System.lineSeparator());
			}
			for (Vacancy vacancy : department.getVacancies()) {
				vacancies.append(vacancy.getVacancyName()).append(".").append(System.lineSeparator());
			}

			content.add(Arrays.asList(department.getId().toString(), department.getDepartmentName(), headHunters.toString(),
					vacancies.toString()));
		}


		ByteArrayInputStream bis = PDFGenerator.customerPDFReport(title, tableHeader, content);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=departments.pdf");

		return ResponseEntity
				.ok()
				.headers(headers)
				.contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
	}

	@PostMapping
	@PreAuthorize("hasAnyAuthority('ADMIN', 'HEADHUNTER')")
	public String add(@Valid Department department, BindingResult bindingResult, Model model) {

		Map<String, String> resultMap = ValidateCaptchaAndPasswordConfirm.getErrors(bindingResult);
		if (!resultMap.isEmpty()) {
			model.addAllAttributes(resultMap);

			Iterable<Department> departments = departmentService.findAllDepartments();
			model.addAttribute("departments", departments);
			return "departments";
		}

		if (departmentService.addDepartment(department)) {
			model.addAttribute("messageType", "success");
			model.addAttribute("message", "Отдел был успешно создан");
		} else {
			model.addAttribute("messageType", "danger");
			model.addAttribute("message", "Отдел с таким название уже создан");
		}

		Iterable<Department> departments = departmentService.findAllDepartments();
		model.addAttribute("departments", departments);
		return "departments";
	}
}