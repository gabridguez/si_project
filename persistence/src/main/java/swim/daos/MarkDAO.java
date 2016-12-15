package swim.daos;

import javax.persistence.EntityManager;

import swim.entities.Mark;

public class MarkDAO extends GenericDAO<Mark> {

	public MarkDAO(EntityManager em) {
		super(em, Mark.class);

	}

}
