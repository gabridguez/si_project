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

	public long getMark() {
		return mark;
	}

	public void setMark(long mark) {
		this.mark = mark;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public SwimmingPool getSwimmingPool() {
		return swimmingPool;
	}

	public void setSwimmingPool(SwimmingPool swimmingPool) {
		this.swimmingPool = swimmingPool;
	}

	public Swimmer getSwimmer() {
		return swimmer;
	}

	public void setSwimmer(Swimmer swimmer) {
		this.swimmer = swimmer;
	}

	public Club getClub() {
		return club;
	}

	public void setClub(Club club) {
		this.club = club;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getId() {
		return id;
	}
	
	
	
}
