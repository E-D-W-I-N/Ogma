package JobHunter.repo;

import JobHunter.domain.Archive;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchiveRepo extends JpaRepository<Archive, Long> {
}
