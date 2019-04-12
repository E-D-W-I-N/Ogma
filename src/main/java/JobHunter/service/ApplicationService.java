package JobHunter.service;

import JobHunter.domain.Application;
import JobHunter.domain.User;
import JobHunter.domain.Vacancy;
import JobHunter.repo.ApplicationRepo;
import JobHunter.repo.VacancyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ApplicationService {

	private final ApplicationRepo applicationRepo;
	private final VacancyRepo vacancyRepo;

	@Autowired
	public ApplicationService(ApplicationRepo applicationRepo, VacancyRepo vacancyRepo) {
		this.applicationRepo = applicationRepo;
		this.vacancyRepo = vacancyRepo;
	}

	public boolean addApplication(Long vacancyId, User user, LocalDate date) {
		Vacancy vacancy = vacancyRepo.findById(vacancyId).orElse(null);

		List<Application> applicationsByUserAndVacancy = applicationRepo.findApplicationsByUserAndVacancy(user, vacancy);
		if (applicationsByUserAndVacancy.size() != 0)
			return false;

		Application application = new Application(vacancy, user, date);
		applicationRepo.save(application);

		return true;
	}

	public List<Application> findApplicationsByUser(User user) {
		return applicationRepo.findApplicationsByUser(user);
	}

	public List<Application> findAllApplications() {
		return applicationRepo.findAll();
	}
}
