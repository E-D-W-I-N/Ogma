package JobHunter.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
public class Interview {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.EAGER, mappedBy = "interview")
	private Application application;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn
	private Department department;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn
	private User headHunter;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn
	private User candidate;

	private LocalDateTime dateTime;

	public Interview() {
	}

	public Interview(Application application, Department department, User headHunter, User candidate, LocalDateTime dateTime) {
		this.application = application;
		this.department = department;
		this.headHunter = headHunter;
		this.candidate = candidate;
		this.dateTime = dateTime;
	}
}
