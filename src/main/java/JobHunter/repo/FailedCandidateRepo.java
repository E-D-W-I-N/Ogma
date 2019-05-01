package JobHunter.repo;

import JobHunter.domain.FailedCandidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FailedCandidateRepo extends JpaRepository<FailedCandidate, Long> {
}
