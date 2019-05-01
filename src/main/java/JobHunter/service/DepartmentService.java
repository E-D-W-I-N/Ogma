package JobHunter.service;

import JobHunter.domain.Department;
import JobHunter.domain.User;
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

	public boolean addDepartment(Department department) {

		if (departmentRepo.findDepartmentByDepartmentName(department.getDepartmentName()) != null)
			return false;

		departmentRepo.save(department);

		return true;
	}

	public List<Department> findAllDepartments() {
		return departmentRepo.findAll();
	}

	public Department findDepartmentByUser(User user) {
		return departmentRepo.findDepartmentByHeadHuntersContains(user);
	}
}
