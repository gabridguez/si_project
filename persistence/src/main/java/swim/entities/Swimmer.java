package swim.entities;

import javax.persistence.*;
import java.util.Collections;
import java.util.Set;

@Entity
public class Swimmer {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	private String surname;
	
	private int birthyear;
	
	private boolean sex;
	
	private String license;

	@OneToMany(mappedBy="swimmer")
	private Set<Mark> marks;

	@ManyToOne(fetch=FetchType.LAZY)
	private Club club;
	
	
	public Set<Mark> getMarks() {
		return Collections.unmodifiableSet(marks);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public int getBirthyear() {
		return birthyear;
	}

	public void setBirthyear(int birthyear) {
		this.birthyear = birthyear;
	}

	public boolean isSex() {
		return sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public Club getClub() {
		return club;
	}

	public void addMark(Mark mark){
		mark.setSwimmer(this);
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

	public void setClub(Club e){
		if(this.club!=null){
			this.club.internalRemoveSwimmer(this);
		}
		this.club=e;
		if(e!=null){
			e.internalAddSwimmer(this);
		}
	}

}
