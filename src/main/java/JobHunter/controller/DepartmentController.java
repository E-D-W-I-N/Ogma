package JobHunter.controller;

import JobHunter.domain.Department;
import JobHunter.service.DepartmentService;
import JobHunter.util.ValidateCaptchaAndPasswordConfirm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
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