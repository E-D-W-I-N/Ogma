package JobHunter.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
public class Archive {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDateTime dateTime;
	private Boolean isSuccess;
	private String result;

	@OneToOne
	@JoinColumn
	private Vacancy vacancy;

	@OneToOne
	@JoinColumn
	private User candidate;

	@OneToOne
	@JoinColumn
	private User headHunter;

	public Archive(LocalDateTime dateTime, Boolean isSuccess, String result, Vacancy vacancy, User candidate, User headHunter) {
		this.dateTime = dateTime;
		this.isSuccess = isSuccess;
		this.result = result;
		this.vacancy = vacancy;
		this.candidate = candidate;
		this.headHunter = headHunter;
	}

	public Archive() {
	}
}
