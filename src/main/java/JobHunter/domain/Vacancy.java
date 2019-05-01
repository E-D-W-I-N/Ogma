package JobHunter.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
public class Vacancy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn
	private Department department;

	private String vacancyName;
	private String description;
	private Float salary;

	@OneToMany(mappedBy = "vacancy", fetch = FetchType.LAZY)
	private Set<Application> applications;

	public Vacancy(Department department, String vacancyName, String description, Float salary) {
		this.department = department;
		this.vacancyName = vacancyName;
		this.description = description;
		this.salary = salary;
	}

	public Vacancy() {
	}
}
