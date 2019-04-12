package JobHunter.repo;

import JobHunter.domain.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacancyRepo extends JpaRepository<Vacancy, Long> {
}
