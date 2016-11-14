package swim.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Event {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	private int poolSize;
	
	private boolean chrono; //true==electronic
	
	private int meters;
	
	public enum strokes{
		BUTTERFLY,CRAWL,BREASTROKE,BACKSTROKE
	}
	
	private strokes stroke; 

	//strokes a;
	//a=strokes.BACKSTROKE;
	//System.out.println(a);
	
	private boolean lap;

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
	
	
	
}
