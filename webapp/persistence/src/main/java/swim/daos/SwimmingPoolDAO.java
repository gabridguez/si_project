package swim.daos;

import javax.persistence.EntityManager;

import swim.entities.SwimmingPool;

public class SwimmingPoolDAO extends GenericDAO<SwimmingPool>{

	public SwimmingPoolDAO(EntityManager em) {
		super(em, SwimmingPool.class);
		
	}

}
