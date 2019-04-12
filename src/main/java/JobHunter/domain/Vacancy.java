package JobHunter.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table
@Data
public class Vacancy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@OneToOne
	@JoinColumn
	private Department department;

	private String vacancyName;
	private String description;
	private Float salary;

	public Vacancy(Department department, String vacancyName, String description, Float salary) {
		this.department = department;
		this.vacancyName = vacancyName;
		this.description = description;
		this.salary = salary;
	}

	public Vacancy() {
	}
}
