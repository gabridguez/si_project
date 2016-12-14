package swim.daos;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import swim.entities.Swimmer;

public class SwimmerDAO  extends GenericDAO<Swimmer>{

	
	
	
	public SwimmerDAO(EntityManager em) {
        super(em, Swimmer.class);
       
}
	

}
