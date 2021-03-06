package swim.entities;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

public class TransactionUtils {

	public static void doTransaction(EntityManagerFactory emf, Transaction transaction) {
		EntityManager em = emf.createEntityManager();				
		EntityTransaction tx = null;
		try{		
			//emp.setName("Daniel");								
			tx = em.getTransaction();			
			try{
				tx.begin();
				transaction.run(em);
				//	em.persist(emp);		
				tx.commit();						
			}finally{
				if (tx !=null && tx.isActive()){ tx.rollback(); }
			}
		}finally{	em.close(); 	}
		
	}
	
	public static void doTransaction(EntityManager em, Transaction transaction) {
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
				transaction.run(em);
			tx.commit();
		} finally {
			if (tx !=null && tx.isActive()){ tx.rollback(); }
		}
	}

}
