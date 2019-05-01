package JobHunter.service;

import JobHunter.domain.Application;
import JobHunter.domain.Department;
import JobHunter.domain.User;
import JobHunter.domain.Vacancy;
import JobHunter.repo.ApplicationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ApplicationService {

	private final ApplicationRepo applicationRepo;

	@Autowired
	public ApplicationService(ApplicationRepo applicationRepo) {
		this.applicationRepo = applicationRepo;
	}

	public boolean addApplication(Vacancy vacancy, User user, LocalDateTime dateTime) {

		List<Application> applicationsByUserAndVacancy = applicationRepo.findApplicationsByUserAndVacancy(user, vacancy);
		if (applicationsByUserAndVacancy.size() != 0)
			return false;

		Application application = new Application(vacancy, vacancy.getDepartment(), user, dateTime);
		applicationRepo.save(application);

		return true;
	}

	public boolean deleteApplication(Application application, User user) {

		if (!(application.getUser().getId().equals(user.getId())))
			return false;

		applicationRepo.deleteById(application.getId());
		return true;
	}

	public List<Application> findApplicationsByUser(User user) {
		return applicationRepo.findApplicationsByUser(user);
	}

	public List<Application> findAllApplications() {
		return applicationRepo.findAll();
	}

	public List<Application> findApplicationsByDepartment(Department department) {
		return applicationRepo.findApplicationsByDepartment(department);
	}

	public Application findApplicationById(Long applicationId) {
		return applicationRepo.findApplicationById(applicationId);
	}
}
