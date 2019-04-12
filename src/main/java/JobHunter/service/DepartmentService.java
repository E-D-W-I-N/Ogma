package JobHunter.service;

import JobHunter.domain.Department;
import JobHunter.repo.DepartmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

	private final DepartmentRepo departmentRepo;

	@Autowired
	public DepartmentService(DepartmentRepo departmentRepo) {
		this.departmentRepo = departmentRepo;
	}

	public boolean addDepartment(String departmentName) {

		if (departmentRepo.findDepartmentByDepartmentName(departmentName) != null)
			return false;

		Department department = new Department(departmentName);
		departmentRepo.save(department);

		return true;
	}

	public List<Department> findAllDepartments() {
		return departmentRepo.findAll();
	}
}
