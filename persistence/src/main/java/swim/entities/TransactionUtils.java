package swim.entities;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import javassist.bytecode.stackmap.BasicBlock.Catch;

public class TransactionUtils {

	public static void doTransaction(EntityManagerFactory emf, Transaction transaction) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = null;
		try {
			tx = em.getTransaction();
			try {
				tx.begin();
				transaction.run(em);
				tx.commit();
				
			} finally {
				if (tx != null && tx.isActive()) {
					tx.rollback();
				}
			}
		} finally {
			em.close();
		}

	}

	public static void doTransaction(EntityManager em, Transaction transaction) {
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			transaction.run(em);
			tx.commit();
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
		}
	}

}
