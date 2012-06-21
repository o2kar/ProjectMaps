package maps.daos;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import maps.model.Node;
import maps.model.Way;

public class RailWayDao extends Dao {

	PreparedStatement insertRailway;
	PreparedStatement insertNode;
	
	public RailWayDao() {
		super();
		try {
			insertRailway = connection.prepareStatement(" INSERT INTO `maps`.`railway` (`id`, `type`) VALUES ( ?, ?);");
			insertNode = connection.prepareStatement("INSERT INTO `maps`.`railwayNodes` (`railWayId`,`longitude`,`latitude`,`posCode`)VALUES(?,?,?,?);");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public synchronized void saveRailway(Way way) {
		try {
			int posCode = 0;
			insertRailway.setString(1, way.getId());
			insertRailway.setString(2, way.getTag("railway"));
			insertRailway.executeUpdate();
			for (Node node : way.getNodes()) {
				insertNode.setInt(1, Integer.valueOf(way.getId()));
				insertNode.setDouble(2, node.getLon());
				insertNode.setDouble(3, node.getLat());
				insertNode.setInt(4, posCode);
				insertNode.executeUpdate();
				posCode++;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void saveRailways(List<Way> ways) {
		for (Way way : ways) {
			this.saveRailway(way);
		}
	}
	
}
