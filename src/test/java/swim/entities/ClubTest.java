package swim.entities;

import static org.junit.Assert.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


public class ClubTest extends SQLBasedTest{

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
	public void testCreateClub() throws SQLException {
		//EntityManager em = emf.createEntityManager();				
		//EntityTransaction tx = null;
		final Club club = new Club();	
		
		swim.entities.TransactionUtils.doTransaction(emf, em ->{
				club.setName("ClubName");
				em.persist(club);
			}
		);
		
		//check
		
		Statement statement = jdbcConnection.createStatement();
		ResultSet rs = statement.executeQuery(
				"SELECT COUNT(*) as total FROM Club WHERE id = "+club.getId());
		rs.next();
		assertEquals(1, rs.getInt("total"));
	}
}
