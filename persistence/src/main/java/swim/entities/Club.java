package swim.entities;

import javax.persistence.*;
import java.util.Collections;
import java.util.Set;

@Entity
public class Club {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;

	private String city;

	private int foundationYear;

	@OneToMany(mappedBy = "club")
	private Set<Mark> marks;

	@OneToMany(mappedBy = "club")
	private Set<SwimmingPool> pools;

	@OneToMany(mappedBy = "club")
	private Set<Swimmer> swimmers;

	public Set<Mark> getMarks() {
		return Collections.unmodifiableSet(marks);
	}

	public Set<SwimmingPool> getPools() {
		return Collections.unmodifiableSet(pools);
	}

	public Set<Swimmer> getSwimmers() {
		return Collections.unmodifiableSet(swimmers);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getFoundationYear() {
		return foundationYear;
	}

	public void setFoundationYear(int foundationYear) {
		this.foundationYear = foundationYear;
	}

	public int getId() {
		return id;
	}

	void internalRemoveMark(Mark mark) {
		this.marks.remove(mark);
	}

	void internalAddMark(Mark mark) {
		this.marks.add(mark);
	}

	public void addMark(Mark mark) {
		mark.setClub(this);
	}

	void internalRemovePool(SwimmingPool swimmingPool) {
		this.pools.remove(swimmingPool);
	}

	void internalAddPool(SwimmingPool pool) {
		this.pools.add(pool);
	}

	public void addPool(SwimmingPool swimmingPool) {
		swimmingPool.setClub(this);
	}

	void internalRemoveSwimmer(Swimmer swimmer) {
		this.swimmers.remove(swimmer);
	}

	void internalAddSwimmer(Swimmer swimmer) {
		this.swimmers.add(swimmer);
	}

	public void addSwimmer(Swimmer swimmer) {
		swimmer.setClub(this);
	}
}
