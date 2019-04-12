package JobHunter.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
@Data
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String departmentName;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "department")
	private Set<HeadHunter> headHunters;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "department")
	private Set<Vacancy> vacancies;

	public Department(String name) {
		this.departmentName = name;
	}

	public Department() {
	}
}
