package JobHunter.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
public class SuccessfulCandidate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn
	private User user;

	@OneToOne
	@JoinColumn
	private Vacancy vacancy;

	private LocalDateTime dateTime;

	public SuccessfulCandidate(User user, Vacancy vacancy, LocalDateTime dateTime) {
		this.user = user;
		this.vacancy = vacancy;
		this.dateTime = dateTime;
	}
}
