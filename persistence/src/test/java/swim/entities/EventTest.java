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

public class EventTest extends SQLBasedTest {
	private static EntityManagerFactory emf;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		emf = Persistence.createEntityManagerFactory("swimTest-database");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		if (emf != null && emf.isOpen())
			emf.close();
	}

	@Test
	public void testCreateEvent() throws SQLException {
		final Event event = new Event();

		swim.entities.TransactionUtils.doTransaction(emf, em -> {
			event.setName("200L");
			event.setChrono(true);
			event.setLap(false);
			event.setMarks(null);
			event.setMeters(200);
			event.setPoolSize(50);
			event.setStroke(Event.strokes.CRAWL);
			em.persist(event);
		});

		// check

		Statement statement = jdbcConnection.createStatement();
		ResultSet rs = statement.executeQuery("SELECT COUNT(*) as total FROM Event WHERE id = " + event.getId());
		rs.next();
		assertEquals(1, rs.getInt("total"));
	}

	@Test
	public void testDeleteEvent() throws SQLException {
		Statement statement = jdbcConnection.createStatement();
		int insertedId = statement.executeUpdate(
				"INSERT INTO Event(name,chrono,lap,meters,poolSize) VALUES('100M',true,false,100,25)",
				Statement.RETURN_GENERATED_KEYS);
		Event event = emf.createEntityManager().find(Event.class, insertedId);
		swim.entities.TransactionUtils.doTransaction(emf, em -> {
			em.remove(em.contains(event) ? event : em.merge(event));
		});

		statement = jdbcConnection.createStatement();
		ResultSet rs = statement.executeQuery("SELECT COUNT(*) as total FROM Event WHERE id = " + event.getId());
		rs.next();
		assertEquals(0, rs.getInt("total"));
	}

}
