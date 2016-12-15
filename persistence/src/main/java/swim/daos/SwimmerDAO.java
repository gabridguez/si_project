package swim.daos;

import javax.persistence.EntityManager;
import swim.entities.Swimmer;

public class SwimmerDAO  extends GenericDAO<Swimmer>{
	
	public SwimmerDAO(EntityManager em) {
        super(em, Swimmer.class);   
}
	

}
