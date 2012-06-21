package maps.daos;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import maps.model.Node;
import maps.model.Way;

public class GreenspaceDao extends Dao {

	PreparedStatement insertGreenspace;
	PreparedStatement insertNode;

	public GreenspaceDao() {
		super();
		try {
			insertGreenspace = connection
					.prepareStatement("INSERT INTO maps.greenspace (id, name, type, subtype) VALUES (?,?,?,?);");
			insertNode = connection
					.prepareStatement("INSERT INTO `maps`.`greenspaceNodes` (`greenspaceId`,`longitude`,`latitude`,`posCode`)VALUES(?,?,?,?);");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public synchronized void saveGreenspace(Way way) {
		try {
			int posCode = 0;
			insertGreenspace.setString(1, way.getId());
			insertGreenspace.setString(2, way.getTag("name"));
			if (way.getTag("natural") != null) {
				insertGreenspace.setString(3, "natural");
				insertGreenspace.setString(4, way.getTag("natural"));
			} else if (way.getTag("leisure") != null) {
				insertGreenspace.setString(3, "leisure");
				insertGreenspace.setString(4, way.getTag("leisure"));
			} else if (way.getTag("landuse") != null) {
				insertGreenspace.setString(3, "landuse");
				insertGreenspace.setString(4, way.getTag("landuse"));
			} else {
				insertGreenspace.setString(3, null);
				insertGreenspace.setString(4, way.getTag(null));
			}
			insertGreenspace.executeUpdate();
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
	
	public void saveGreenspaces(List<Way> ways) {
		for (Way way : ways) {
			this.saveGreenspace(way);
		}
	}
}
