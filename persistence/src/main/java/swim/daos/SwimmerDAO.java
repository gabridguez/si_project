package swim.daos;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import swim.entities.Swimmer;

public class SwimmerDAO {

	//private static EntityManagerFactory emf;
	private static EntityManager em;
	
	
	public SwimmerDAO(EntityManager em) {
        //emf = Persistence.createEntityManagerFactory("swim-database");
        this.em = em;
}
	public void createSwimmer(Swimmer s) {
		swim.entities.TransactionUtils.doTransaction(em, em -> {
			em.persist(s);
		});
	}

	public Swimmer findSwimmer(int id) {
		return this.em.find(Swimmer.class, id);
	}

	public void removeSwimmer(int id) {
		Swimmer s = findSwimmer(id);
		if (s != null) {
			//emf.createEntityManager().remove(s);
			swim.entities.TransactionUtils.doTransaction(this.em, em->{
				
				em.remove(em.contains(s) ? s : em.merge(s));
			});
		}
	}
	
	public HashSet<Swimmer> findAllSwimmers() {
	    Query query = this.em.createQuery("SELECT e FROM Swimmer e");
	    return new HashSet<Swimmer>(query.getResultList());
	  }

}
