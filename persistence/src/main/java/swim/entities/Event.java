package swim.entities;

import java.util.Collections;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Event {

	public enum strokes {
		BUTTERFLY, CRAWL, BREASTROKE, BACKSTROKE
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;

	private int poolSize;

	private boolean chrono; // true==electronic

	private int meters;

	private strokes stroke;

	@OneToMany(mappedBy = "event")
	private Set<Mark> marks;

	private boolean lap;

	public Set<Mark> getMarks() {
		return Collections.unmodifiableSet(marks);
	}

	public void setMarks(Set<Mark> marks) {
		this.marks = marks;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPoolSize() {
		return poolSize;
	}

	public void setPoolSize(int poolSize) {
		this.poolSize = poolSize;
	}

	public boolean isChrono() {
		return chrono;
	}

	public void setChrono(boolean chrono) {
		this.chrono = chrono;
	}

	public int getMeters() {
		return meters;
	}

	public void setMeters(int meters) {
		this.meters = meters;
	}

	public strokes getStroke() {
		return stroke;
	}

	public void setStroke(strokes stroke) {
		this.stroke = stroke;
	}

	public boolean isLap() {
		return lap;
	}

	public void setLap(boolean lap) {
		this.lap = lap;
	}

	public int getId() {
		return id;
	}

	public void addMark(Mark mark) {
		mark.setEvent(this);
	}

	public void removeMark(Mark mark) {
		mark.setEvent(null);
	}

	void internalRemoveMark(Mark mark) {
		this.marks.remove(mark);
	}

	void internalAddMark(Mark mark) {
		this.marks.add(mark);
	}
}
