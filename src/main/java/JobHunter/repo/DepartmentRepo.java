package JobHunter.repo;

import JobHunter.domain.Department;
import JobHunter.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepo extends JpaRepository<Department, Long> {
	Department findDepartmentByDepartmentName(String departmentName);

	Department findDepartmentByHeadHuntersContains(User user);
}