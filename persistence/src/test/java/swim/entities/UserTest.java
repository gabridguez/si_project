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

public class UserTest extends SQLBasedTest {
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

	@After
	public void renewConnectionAfterTest() throws ClassNotFoundException, SQLException {
		super.renewConnection();
	}

	@Test
	public void testCreateUser() throws SQLException {
		final User user = new User();

		swim.entities.TransactionUtils.doTransaction(emf, em -> {
			user.setEmail("user@user.com");
			user.setLogin("user1");
			user.setName("User");
			user.setPass("asdfasdfasdfasdf");
			user.setType(User.UserType.USER);
			em.persist(user);
		});

		Statement statement = jdbcConnection.createStatement();
		ResultSet rs = statement.executeQuery("SELECT COUNT(*) as total FROM User WHERE id = " + user.getId());
		rs.next();
		assertEquals(1, rs.getInt("total"));
	}

	@Test
	public void testDeleteUser() throws SQLException {
		Statement statement = jdbcConnection.createStatement();
		int insertedId = statement.executeUpdate(
				"INSERT INTO User(name,email,login,pass) VALUES('nombre','email','login','pass')",
				Statement.RETURN_GENERATED_KEYS);
		User user = emf.createEntityManager().find(User.class, insertedId);
		swim.entities.TransactionUtils.doTransaction(emf, em -> {
			em.remove(em.contains(user) ? user : em.merge(user));
		});
		statement = jdbcConnection.createStatement();
		ResultSet rs = statement.executeQuery("SELECT COUNT(*) as total FROM Mark WHERE id = " + user.getId());
		rs.next();
		assertEquals(0, rs.getInt("total"));
	}

}
