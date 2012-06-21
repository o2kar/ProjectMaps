package maps.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Abstract DAO class
 * @author rroesch
 *
 */
public abstract class Dao {
	
	protected Connection connection;

	public Dao() {
		super();
		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost/maps", "maps", "maps");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
