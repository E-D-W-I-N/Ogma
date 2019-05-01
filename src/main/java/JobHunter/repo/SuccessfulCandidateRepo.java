package JobHunter.repo;

import JobHunter.domain.SuccessfulCandidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuccessfulCandidateRepo extends JpaRepository<SuccessfulCandidate, Long> {
}
