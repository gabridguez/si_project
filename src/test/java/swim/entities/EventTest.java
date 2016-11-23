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

public class EventTest extends SQLBasedTest{
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
	public void testCreateEvent() throws SQLException {
		//EntityManager em = emf.createEntityManager();				
		//EntityTransaction tx = null;
		final Event event = new Event();	
		
		swim.entities.TransactionUtils.doTransaction(emf, em ->{
			event.setName("200L");
			event.setChrono(true);
			event.setLap(false);
			event.setMarks(null);
			event.setMeters(200);
			event.setPoolSize(50);
			event.setStroke(Event.strokes.CRAWL);
				em.persist(event);
			}
		);
		
		//check
		
		Statement statement = jdbcConnection.createStatement();
		ResultSet rs = statement.executeQuery(
				"SELECT COUNT(*) as total FROM Event WHERE id = "+event.getId());
		rs.next();
		assertEquals(1, rs.getInt("total"));
	}

}
