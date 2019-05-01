package JobHunter.controller;

import JobHunter.domain.Archive;
import JobHunter.repo.ArchiveRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/archive")
public class ArchiveController {

	private final ArchiveRepo archiveRepo;

	@Autowired
	public ArchiveController(ArchiveRepo archiveRepo) {
		this.archiveRepo = archiveRepo;
	}

	@GetMapping
	public String archive(Model model) {
		Iterable<Archive> archives = archiveRepo.findAll();

		model.addAttribute("archives", archives);
		return "archive";
	}
}
