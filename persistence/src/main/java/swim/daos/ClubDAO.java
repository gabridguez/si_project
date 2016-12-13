package swim.daos;

import java.util.HashSet;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import swim.entities.Club;

public class ClubDAO extends GenericDAO<Club>{

   

    public ClubDAO(EntityManager entityManager)
    {
    	super(entityManager, Club.class);
       
    }

   



}