package swim.entities;

import static org.junit.Assert.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class MarkTest extends SQLBasedTest{
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
	public void testLazyInizalizationException() throws SQLException{
		/*recuperar un evento en una transaccion, entonces nos cerrará la transaccion 
		y quedará en estado detached, si accedemos a la lista de marcas del event, al ser lazy,
		fallará
		*/
		swim.entities.TransactionUtils.doTransaction(emf,em->{
			
		});
	}
	
	@Test
	public void testCreateMark() throws SQLException{
		//Club testClub=new Club();
		//Event testEvent=new Event();
		//Swimmer testSwimmer=new Swimmer();
		//SwimmingPool testPool=new SwimmingPool();
		Mark mark= new Mark();
		swim.entities.TransactionUtils.doTransaction(emf, em ->{
			
			
			/*testClub.setCity("city");
			testClub.setFoundationYear(2000);
			testClub.setName("testPool");
			em.persist(testClub);
			
			testEvent.setChrono(true);
			testEvent.setLap(false);
			testEvent.setMeters(200);
			testEvent.setName("200M");
			testEvent.setPoolSize(50);
			testEvent.setStroke(Event.strokes.CRAWL);
			em.persist(testEvent);
			
			testSwimmer.setBirthyear(1990);
			testSwimmer.setClub(testClub);
			testSwimmer.setLicense("999900023");
			testSwimmer.setName("name");
			testSwimmer.setSex(false);
			testSwimmer.setSurname("surname");
			em.persist(testSwimmer);
			
			testPool.setAddress("calle falsa 123");
			testPool.setClub(testClub);
			testPool.setName("poolname");
			testPool.setSize(50);
			em.persist(testPool);*/
			
			
			//mark.setClub(testClub);
			mark.setDate(LocalDate.now());
			//mark.setEvent(testEvent);
			mark.setMark(140503);
			//mark.setSwimmer(testSwimmer);
			//mark.setSwimmingPool(testPool);
			em.persist(mark);
		}
	);
		
		Statement statement = jdbcConnection.createStatement();
		ResultSet rs = statement.executeQuery(
				"SELECT COUNT(*) as total FROM Mark WHERE id = "+mark.getId());
		rs.next();
		assertEquals(1, rs.getInt("total"));
	}
	
	@Test
	public void testDeleteMark() throws SQLException{
		Statement statement = jdbcConnection.createStatement();
		int insertedId = statement.executeUpdate("INSERT INTO Mark(mark,date) VALUES(1394294,now())",
                Statement.RETURN_GENERATED_KEYS);
		Mark mark= emf.createEntityManager().find(Mark.class, insertedId);
		swim.entities.TransactionUtils.doTransaction(emf, em->{
			//Club club=em.find(Club.class, insertedId);
			em.remove(em.contains(mark) ? mark : em.merge(mark));
		});
		
		
		statement = jdbcConnection.createStatement();
		ResultSet rs = statement.executeQuery(
				"SELECT COUNT(*) as total FROM Mark WHERE id = "+mark.getId());
		rs.next();
		assertEquals(0, rs.getInt("total"));
	}
}
