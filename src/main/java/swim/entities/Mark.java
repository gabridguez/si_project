package swim.entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Mark {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private long mark;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Event event;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private SwimmingPool swimmingPool;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Swimmer swimmer;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Club club;
	
	private LocalDate date;
	
}
