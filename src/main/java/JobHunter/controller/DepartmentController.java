package JobHunter.controller;

import JobHunter.domain.Department;
import JobHunter.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String add(@RequestParam String departmentName, Model model) {

		if (!departmentService.addDepartment(departmentName)) {
			model.addAttribute("message", "Department with this name was already created!");
			return "departments";
		}

		Iterable<Department> departments = departmentService.findAllDepartments();

		model.addAttribute("departments", departments);
		return "departments";
	}
}