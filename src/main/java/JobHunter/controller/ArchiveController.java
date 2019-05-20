package JobHunter.controller;

import JobHunter.domain.Archive;
import JobHunter.service.ArchiveService;
import JobHunter.util.PDFGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/archive")
@PreAuthorize("hasAnyAuthority('ADMIN', 'HEADHUNTER')")
public class ArchiveController {

	private final ArchiveService archiveService;

	@Autowired
	public ArchiveController(ArchiveService archiveService) {
		this.archiveService = archiveService;
	}

	@GetMapping
	public String archive(Model model) {
		Iterable<Archive> archives = archiveService.findAllArchives();
		model.addAttribute("archives", archives);
		return "archive";
	}

	@GetMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> archiveReport() {
		List<Archive> archives = archiveService.findAllArchives();

		String title = "Архив";
		List<String> tableHeader = Arrays.asList("ID", "Дата", "Время", "Пройдено/Провалено", "Результат", "Вакансия",
				"Кандидат", "Работник");

		List content = new ArrayList();
		for (Archive archive : archives) {
			content.add(Arrays.asList(archive.getId().toString(), archive.getDateTime().toLocalDate().toString(),
					archive.getDateTime().toLocalTime().toString().substring(0, 8), archive.getIsSuccess().toString(),
					archive.getResult(), archive.getVacancy().getVacancyName(), archive.getCandidate().getUsername(),
					archive.getHeadHunter().getUsername()));
		}


		ByteArrayInputStream bis = PDFGenerator.customerPDFReport(title, tableHeader, content);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=archive.pdf");

		return ResponseEntity
				.ok()
				.headers(headers)
				.contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
	}
}
