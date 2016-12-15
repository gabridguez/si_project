package swim.entities;

import static org.junit.Assert.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class SwimmerTest extends SQLBasedTest{
	
	private static EntityManagerFactory emf;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		emf = Persistence.createEntityManagerFactory("swimTest-database");
	}	
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		if(emf!=null && emf.isOpen()) emf.close();
	}
	@After
	public void renewConnectionAfterTest() throws ClassNotFoundException, SQLException {
		super.renewConnection();
	}
	
	@Test
	public void testCreateSwimmer() throws SQLException {
		
		final Swimmer swimmer = new Swimmer();	
		
		swim.entities.TransactionUtils.doTransaction(emf, em ->{
				swimmer.setName("pepito");
				swimmer.setSurname("reinoso");
				swimmer.setBirthyear(1992);
				swimmer.setSex(true);
				swimmer.setLicense("28374RJ3");
				em.persist(swimmer);
			}
		);
		Statement statement = jdbcConnection.createStatement();
		ResultSet rs = statement.executeQuery(
				"SELECT COUNT(*) as total FROM Swimmer WHERE id = "+swimmer.getId());
		rs.next();
		assertEquals(1, rs.getInt("total"));
	}

    @Test
    public void testFindById() throws SQLException{
        Statement statement = jdbcConnection.createStatement();
        int insertedId = statement.executeUpdate(
                "INSERT INTO Swimmer(name,surname,birthyear,sex,license) VALUES('pepito','reinoso',1992,true,'28374RJ3')",Statement.RETURN_GENERATED_KEYS);

        Swimmer swimmer = emf.createEntityManager().find(Swimmer.class, insertedId);

        assertEquals("pepito", swimmer.getName());
        assertEquals("reinoso", swimmer.getSurname());
        assertEquals(1992, swimmer.getBirthyear());
        assertEquals(true, swimmer.isSex());
        assertEquals("28374RJ3", swimmer.getLicense());
    }

	@Test
	public void testDeleteSwimmer() throws SQLException{
		
		//prepare database for test
				Statement statement = jdbcConnection.createStatement();
				statement.executeUpdate("INSERT INTO Swimmer(name,surname,birthYear,sex,license) VALUES('pepito','reinoso',1992,true,'28374RJ3')",
		                Statement.RETURN_GENERATED_KEYS);
				int id = getLastInsertedId(statement);
				
				swim.entities.TransactionUtils.doTransaction(emf, em -> {
					Swimmer s = em.find(Swimmer.class, id);
					em.remove(s);
				});
				
				//check
				statement = jdbcConnection.createStatement();
				ResultSet rs = statement.executeQuery(
						"SELECT COUNT(*) as total FROM Swimmer WHERE id = "+id);
				rs.next();
				
				assertEquals(0, rs.getInt("total"));
				
	}

}
