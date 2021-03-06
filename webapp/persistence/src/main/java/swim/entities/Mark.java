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

	//el atributo es el del lado one
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

	public SwimmingPool getSwimmingPool() {
		return swimmingPool;
	}

	public Swimmer getSwimmer() {
		return swimmer;
	}

	public Club getClub() {
		return club;
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

	public void setEvent(Event e){
		if(this.event!=null){
			this.event.internalRemoveMark(this);
		}
		this.event=e;
		if(e!=null){
			e.internalAddMark(this);
		}
	}

	public void setSwimmingPool(SwimmingPool e){
		if(this.swimmingPool!=null){
			this.swimmingPool.internalRemoveMark(this);
		}
		this.swimmingPool=e;
		if(e!=null){
			e.internalAddMark(this);
		}
	}

	public void setSwimmer(Swimmer e){
		if(this.swimmer!=null){
			this.swimmer.internalRemoveMark(this);
		}
		this.swimmer=e;
		if(e!=null){
			e.internalAddMark(this);
		}
	}

	public void setClub(Club e){
		if(this.club!=null){
			this.club.internalRemoveMark(this);
		}
		this.club=e;
		if(e!=null){
			e.internalAddMark(this);
		}
	}	
}
