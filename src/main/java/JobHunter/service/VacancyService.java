package JobHunter.service;

import JobHunter.domain.Department;
import JobHunter.domain.Vacancy;
import JobHunter.repo.DepartmentRepo;
import JobHunter.repo.VacancyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VacancyService {

	private final VacancyRepo vacancyRepo;
	private final DepartmentRepo departmentRepo;

	@Autowired
	public VacancyService(VacancyRepo vacancyRepo, DepartmentRepo departmentRepo) {
		this.vacancyRepo = vacancyRepo;
		this.departmentRepo = departmentRepo;
	}

	public void addVacancy(Long departmentId, String vacancyName, String description, Float salary) {
		Department departmentFromDb = departmentRepo.findById(departmentId).orElse(null);

		Vacancy vacancy = new Vacancy(departmentFromDb, vacancyName, description, salary);
		vacancyRepo.save(vacancy);
	}
}