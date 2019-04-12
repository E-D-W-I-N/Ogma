package JobHunter.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
@Data
public class Application {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn
	private Vacancy vacancy;
	@JoinColumn
	@ManyToOne(fetch = FetchType.EAGER)
	private User user;
	private LocalDate date;

	public Application() {
	}

	public Application(Vacancy vacancy, User user, LocalDate date) {
		this.vacancy = vacancy;
		this.user = user;
		this.date = date;
	}
}
