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

public class SwimmerTest extends SQLBasedTest{
	
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
		
		//check
		
		Statement statement = jdbcConnection.createStatement();
		ResultSet rs = statement.executeQuery(
				"SELECT COUNT(*) as total FROM Swimmer WHERE id = "+swimmer.getId());
		rs.next();
		assertEquals(1, rs.getInt("total"));
	}
	
	@Test
	public void testFindById() throws Exception{
		Statement statement = jdbcConnection.createStatement();
		int insertedId = this.idGen(statement);
		Swimmer swimmer = new Swimmer();
		try{
			swimmer= emf.createEntityManager().find(Swimmer.class, insertedId);//esta tirando nulo
		}catch(RuntimeException ex ){
			throw new Exception(ex);
		}
		assertEquals("pepito", swimmer.getName());
		assertEquals("reinoso", swimmer.getSurname());
		assertEquals(1992, swimmer.getBirthyear());
		assertEquals(true, swimmer.isSex());
		assertEquals("28374RJ3", swimmer.getLicense());
		assertEquals(insertedId, swimmer.getId());
	}
	@Test
	public void testDeleteSwimmer() throws SQLException{
		Statement statement = jdbcConnection.createStatement();
		int insertedId = this.idGen(statement);
		
		Swimmer swimmer= (Swimmer) emf.createEntityManager().find(Swimmer.class, insertedId);
		swim.entities.TransactionUtils.doTransaction(emf, em->{
			em.remove(em.contains(swimmer) ? swimmer : em.merge(swimmer));
		});

		statement = jdbcConnection.createStatement();
		ResultSet rs = statement.executeQuery(
				"SELECT COUNT(*) as total FROM Swimmer WHERE id = "+swimmer.getId());
		rs.next();
		assertEquals(0, rs.getInt("total"));
	}

	private int idGen(Statement statement) throws SQLException
	{
		int id;
	
		id = statement.executeUpdate(
					"INSERT INTO Swimmer(name,surname,birthYear,sex,license) VALUES('pepito','reinoso',1992,true,'28374RJ3')",
					Statement.RETURN_GENERATED_KEYS);
		
		return id;
	}
}
