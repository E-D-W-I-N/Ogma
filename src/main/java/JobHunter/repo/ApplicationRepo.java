package JobHunter.repo;

import JobHunter.domain.Application;
import JobHunter.domain.User;
import JobHunter.domain.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepo extends JpaRepository<Application, Long> {
	List<Application> findApplicationsByUser(User user);

	List<Application> findApplicationsByUserAndVacancy(User user, Vacancy vacancy);
}
