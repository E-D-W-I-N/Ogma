package JobHunter.service;

import JobHunter.domain.Department;
import JobHunter.domain.Vacancy;
import JobHunter.repo.VacancyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VacancyService {

	private final VacancyRepo vacancyRepo;

	@Autowired
	public VacancyService(VacancyRepo vacancyRepo) {
		this.vacancyRepo = vacancyRepo;
	}

	public void addVacancy(Department department, String vacancyName, String description, Float salary) {
		Vacancy vacancy = new Vacancy(department, vacancyName, description, salary);
		vacancyRepo.save(vacancy);
	}

	public List<Vacancy> findAllVacancies() {
		return vacancyRepo.findAll();
	}
}