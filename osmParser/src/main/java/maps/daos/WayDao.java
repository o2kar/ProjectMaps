package maps.daos;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import maps.model.Node;
import maps.model.Way;

public class WayDao extends Dao {

	private PreparedStatement insertWay;
	private PreparedStatement insertWayNode;

	public WayDao() {
		try {
			insertWay = connection
					.prepareStatement("INSERT INTO way (id, name, type) VALUES(?,?,?);");
			insertWayNode = connection
					.prepareStatement("INSERT INTO wayNodes (wayId,longitude, latitude, posCode, trainStationName) VALUES(?,?,?,?,?);");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public synchronized void saveWay(Way way) {
		try {
			int posCode = 0;
			insertWay.setString(1, way.getId());
			insertWay.setString(2, way.getName());
			insertWay.setString(3, way.getHighway());
			insertWay.executeUpdate();
			for (Node node : way.getNodes()) {
				insertWayNode.setInt(1, Integer.valueOf(way.getId()));
				insertWayNode.setDouble(2, node.getLon());
				insertWayNode.setDouble(3, node.getLat());
				insertWayNode.setInt(4, posCode);
				insertWayNode.setString(5, null);
				insertWayNode.executeUpdate();
				posCode++;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void saveWays(List<Way> ways) {
		for (Way way : ways) {
			this.saveWay(way);
		}
	}

}
