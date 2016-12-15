package swim.daos;

import javax.persistence.EntityManager;
import swim.entities.Event;

public class EventDAO extends GenericDAO<Event> {

	public EventDAO(EntityManager em) {
		super(em, Event.class);
	}

}