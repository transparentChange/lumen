package bookmarks.lumen.domain;

import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Parameter

import javax.persistence.Column
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.OneToOne

@Entity
@Table(name="sequence_number")
@GenericGenerator(name = "PositionSequenceGenerator",
	strategy = "bookmarks.lumen.data.PositionSequenceGenerator",
	parameters = @Parameter(name = "sequence-generator", value = "position_sequence")
)
public class SequenceNumber {
	@Id
	@Column(name = "number")
	@GeneratedValue(generator = "PositionSequenceGenerator")
	private Long id
	
	SequenceNumber() {
	}
	
	SequenceNumber(Long id) {
		this.id = id
	}

	public Long getId() {
		return id
	}

	public void setId(Long id) {
		this.id = id
	}
}
