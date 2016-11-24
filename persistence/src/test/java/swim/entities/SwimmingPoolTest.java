package swim.entities;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;

/**
 * Created by Andoni Da Silva on 14/11/2016.
 */
public class SwimmingPoolTest extends SQLBasedTest{

    private static EntityManagerFactory emf;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        emf = Persistence.createEntityManagerFactory("swim-database");
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        if(emf!=null && emf.isOpen()) emf.close();
    }

    @Test
    public void testCreatePool() throws SQLException {
        //EntityManager em = emf.createEntityManager();
        //EntityTransaction tx = null;
        final SwimmingPool pool = new SwimmingPool();

        swim.entities.TransactionUtils.doTransaction(emf, em ->{
                    pool.setName("mos");
                    pool.setAddress("mos");
                    pool.setSize(90);
                    em.persist(pool);
                }
        );

        //check

        Statement statement = jdbcConnection.createStatement();
        ResultSet rs = statement.executeQuery(
                "SELECT COUNT(*) as total FROM SwimmingPool WHERE id = "+pool.getId());
        rs.next();
        assertEquals(1, rs.getInt("total"));
    }

    @Test
    public void testFindById() throws SQLException{
        Statement statement = jdbcConnection.createStatement();
        int insertedId = statement.executeUpdate(
                "INSERT INTO SwimmingPool(name,address,size) VALUES('mos','mos',90)",Statement.RETURN_GENERATED_KEYS);

        SwimmingPool pool = emf.createEntityManager().find(SwimmingPool.class, insertedId);

        assertEquals("mos", pool.getName());
        assertEquals("mos",pool.getAddress());
        assertEquals(90, pool.getSize());
    }
    @Test
    public void testDeletepool() throws SQLException{
        Statement statement = jdbcConnection.createStatement();
        int insertedId = statement.executeUpdate(
                "INSERT INTO SwimmingPool(name,address,size) VALUES('mos','mos',90)",Statement.RETURN_GENERATED_KEYS);

       SwimmingPool pool= emf.createEntityManager().find(SwimmingPool.class, insertedId);
        swim.entities.TransactionUtils.doTransaction(emf, em->{
            //Club club=em.find(Club.class, insertedId);
            em.remove(em.contains(pool) ? pool : em.merge(pool));
        });

        statement = jdbcConnection.createStatement();
        ResultSet rs = statement.executeQuery(
                "SELECT COUNT(*) as total FROM SwimmingPool WHERE id = "+pool.getId());
        rs.next();
        assertEquals(0, rs.getInt("total"));
    }
}
