package JobHunter.service;

import JobHunter.domain.*;
import JobHunter.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InterviewService {

	private final InterviewRepo interviewRepo;
	private final ApplicationRepo applicationRepo;
	private final ArchiveRepo archiveRepo;
	private final SuccessfulCandidateRepo successfulCandidateRepo;
	private final FailedCandidateRepo failedCandidateRepo;

	@Autowired
	public InterviewService(InterviewRepo interviewRepo, ApplicationRepo applicationRepo, ArchiveRepo archiveRepo,
							SuccessfulCandidateRepo successfulCandidateRepo, FailedCandidateRepo failedCandidateRepo) {
		this.interviewRepo = interviewRepo;
		this.applicationRepo = applicationRepo;
		this.archiveRepo = archiveRepo;
		this.successfulCandidateRepo = successfulCandidateRepo;
		this.failedCandidateRepo = failedCandidateRepo;
	}

	public boolean addInterview(Application application, User user, LocalDateTime dateTime) {

		Interview interviewFromDb = interviewRepo.findInterviewByHeadHunterAndApplication(user, application);
		if (interviewFromDb != null)
			return false;

		Interview interview = new Interview(application, application.getDepartment(), user, application.getUser(), dateTime);
		application.setInterview(interview);
		interviewRepo.save(interview);

		return true;
	}

	public boolean addResult(Interview interview, Boolean isSuccess, String result) {
		if (result.isBlank())
			return false;

		Archive archive = new Archive(LocalDateTime.now(), isSuccess, result, interview.getApplication().getVacancy(),
				interview.getApplication().getUser(), interview.getHeadHunter());
		archiveRepo.save(archive);

		if (isSuccess) {
			SuccessfulCandidate successfulCandidate = new SuccessfulCandidate(interview.getApplication().getUser(),
					interview.getApplication().getVacancy(), LocalDateTime.now());
			successfulCandidateRepo.save(successfulCandidate);
		} else {
			FailedCandidate failedCandidate = new FailedCandidate(interview.getApplication().getUser(),
					interview.getApplication().getVacancy(), LocalDateTime.now());
			failedCandidateRepo.save(failedCandidate);
		}

		applicationRepo.delete(interview.getApplication());
		return true;
	}

	public List<Interview> findInterviewsByDepartment(Department department) {
		return interviewRepo.findInterviewsByDepartment(department);
	}

	public List<Interview> findInterviewsByCandidate(User user) {
		return interviewRepo.findInterviewsByCandidate(user);
	}

	public List<Interview> findAllInterviews() {
		return interviewRepo.findAll();
	}
}
