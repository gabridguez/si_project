package swim.daos;

import java.util.HashSet;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public abstract class GenericDAO<T> {

	protected static EntityManager em;
	protected Class<T> entityClass;

	public GenericDAO(EntityManager em, Class<T> entityClass) {
		this.entityClass = entityClass;
		this.em = em;
	}

	public void create(T s) {
		swim.entities.TransactionUtils.doTransaction(em, em -> {
			em.persist(s);
		});
	}

	public T find(int id) {
		return this.em.find(entityClass, id);
	}

	public void remove(int id) {
		T s = find(id);
		if (s != null) {
			swim.entities.TransactionUtils.doTransaction(this.em, em -> {
				em.remove(em.contains(s) ? s : em.merge(s));
			});
		}
	}

	public HashSet<T> findAll() {
		Query query = this.em.createQuery("SELECT e FROM " + entityClass.getName() + " e");
		return new HashSet<T>(query.getResultList());
	}

}
