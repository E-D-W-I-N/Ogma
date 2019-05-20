package JobHunter.service;

import JobHunter.domain.Archive;
import JobHunter.repo.ArchiveRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArchiveService {

	private final ArchiveRepo archiveRepo;

	@Autowired
	public ArchiveService(ArchiveRepo archiveRepo) {
		this.archiveRepo = archiveRepo;
	}

	public List<Archive> findAllArchives() {
		return archiveRepo.findAll();
	}
}
