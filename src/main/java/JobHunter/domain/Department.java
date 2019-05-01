package JobHunter.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Department's Name can't be empty")
	private String departmentName;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "department")
	private Set<User> headHunters;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "department")
	private Set<Vacancy> vacancies;

	public Department(String name) {
		this.departmentName = name;
	}

	public Department() {
	}
}
