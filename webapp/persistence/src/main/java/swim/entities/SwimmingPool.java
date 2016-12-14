package swim.entities;

import javax.persistence.*;
import java.util.Collections;
import java.util.Set;

@Entity
public class SwimmingPool {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	private String address;
	
	private int size;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Club club;

	@OneToMany(mappedBy="swimmingPool")
	private Set<Mark> marks;

	
	public Set<Mark> getMarks() {
		return Collections.unmodifiableSet(marks);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Club getClub() {
		return club;
	}


	public int getId() {
		return id;
	}

	void internalRemoveMark(Mark mark){
		this.marks.remove(mark);
	}

	void internalAddMark(Mark mark){
		this.marks.add(mark);
	}

	public void addMark(Mark mark){
		mark.setSwimmingPool(this);
	}

	public void setClub(Club e){
        if(this.club!=null){
            this.club.internalRemovePool(this);
        }
        this.club=e;
        if(e!=null){
            e.internalAddPool(this);
        }
    }
	
}
