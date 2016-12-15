package swim.daos;

import javax.persistence.EntityManager;
import swim.entities.Club;

public class ClubDAO extends GenericDAO<Club>{

    public ClubDAO(EntityManager entityManager)
    {
    	super(entityManager, Club.class);  
    }
}