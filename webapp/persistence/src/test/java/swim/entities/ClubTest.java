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
				club.setCity("ClubCity");
				club.setFoundationYear(1999);
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
	
	@Test
	public void testFindById() throws SQLException{
		Statement statement = jdbcConnection.createStatement();
		int insertedId = statement.executeUpdate(
				"INSERT INTO Club(name,city,foundationYear) VALUES('ClubName','ClubCity',1999)",Statement.RETURN_GENERATED_KEYS);
		
		Club club= emf.createEntityManager().find(Club.class, insertedId);
		
		assertEquals("ClubName", club.getName());
		assertEquals("ClubCity", club.getCity());
		assertEquals(1999, club.getFoundationYear());
		assertEquals(insertedId,club.getId());
	}
	@Test
	public void testDeleteClub() throws SQLException{
		Statement statement = jdbcConnection.createStatement();
		int insertedId = statement.executeUpdate(
				"INSERT INTO Club(name,city,foundationYear) VALUES('ClubName','ClubCity',1999)",Statement.RETURN_GENERATED_KEYS);
		
		Club club= emf.createEntityManager().find(Club.class, insertedId);
		swim.entities.TransactionUtils.doTransaction(emf, em->{
			//Club club=em.find(Club.class, insertedId);
			em.remove(em.contains(club) ? club : em.merge(club));
		});
		
		
		statement = jdbcConnection.createStatement();
		ResultSet rs = statement.executeQuery(
				"SELECT COUNT(*) as total FROM Club WHERE id = "+club.getId());
		rs.next();
		assertEquals(0, rs.getInt("total"));
	}
	
	
	
	
	
	
}
