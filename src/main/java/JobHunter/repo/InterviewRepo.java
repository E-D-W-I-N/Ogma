package JobHunter.repo;

import JobHunter.domain.Application;
import JobHunter.domain.Department;
import JobHunter.domain.Interview;
import JobHunter.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterviewRepo extends JpaRepository<Interview, Long> {

	Interview findInterviewByHeadHunterAndApplication(User user, Application application);

	List<Interview> findInterviewsByDepartment(Department department);

	List<Interview> findInterviewsByCandidate(User user);
}
