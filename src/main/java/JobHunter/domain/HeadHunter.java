package JobHunter.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table
@Data
class HeadHunter {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@ManyToOne
	@JoinColumn
	private Department department;

	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@JoinColumn
	@OneToOne(cascade = CascadeType.ALL)
	private User user;
}