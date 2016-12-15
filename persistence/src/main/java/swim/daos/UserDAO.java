package swim.daos;

import javax.persistence.EntityManager;
import swim.entities.User;

public class UserDAO extends GenericDAO<User> {

	public UserDAO(EntityManager em) {
		super(em, User.class);
	}

}
