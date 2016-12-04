package swim.daos;

import java.util.HashSet;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import swim.entities.Club;

public class ClubDAO{

    private EntityManager em;

    public ClubDAO(EntityManager entityManager)
    {
        this.em = entityManager;
    }

    public void createClub(Club club) {
        swim.entities.TransactionUtils.doTransaction(em, em -> {
            em.persist(club);
        });
    }

    public Club findClub(int id) {
        return this.em.find(Club.class, id);
    }

    public void removeClub(int id) {
        Club c = findClub(id);
        if (c != null) {
            swim.entities.TransactionUtils.doTransaction(this.em, em->{

                em.remove(em.contains(c) ? c : em.merge(c));
            });
        }
    }

    public HashSet<Club> findAllClubs() {
        Query query = this.em.createQuery("SELECT e FROM Club e");
        return new HashSet<Club>(query.getResultList());
    }



}