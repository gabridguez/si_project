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

	public Set<Mark> getMarks() {
		return Collections.unmodifiableSet(marks);
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Club club;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	String getSurname() {
		return surname;
	}

	void setSurname(String surname) {
		this.surname = surname;
	}

	int getBirthyear() {
		return birthyear;
	}

	void setBirthyear(int birthyear) {
		this.birthyear = birthyear;
	}

	boolean isSex() {
		return sex;
	}

	void setSex(boolean sex) {
		this.sex = sex;
	}

	String getLicense() {
		return license;
	}

	void setLicense(String license) {
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

	void setClub(Club e){
		if(this.club!=null){
			this.club.internalRemoveSwimmer(this);
		}
		this.club=e;
		if(e!=null){
			e.internalAddSwimmer(this);
		}
	}

}
