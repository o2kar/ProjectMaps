package maps.mains;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import maps.daos.GreenspaceDao;
import maps.daos.WayDao;
import maps.helper.FileStreamFactory;
import maps.model.Node;
import maps.model.Way;
import maps.parser.NodeParser;
import maps.parser.WayParser;

public class GreenspaceMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FileInputStream in;
		try {
			GreenspaceDao dao = new GreenspaceDao();
			in = FileStreamFactory.getStream();
			HashMap<String, Node> nodeMap = new NodeParser(in, 48.97, 49.05,
					8.33, 8.49).parseNodes();
			in.close();

			printMemoryUsage();

			WayParser wp = new WayParser(in, nodeMap);
			wp.addTagFilter("leisure", "common");
			wp.addTagFilter("leisure", "dog_park");
			wp.addTagFilter("leisure", "garden");
			wp.addTagFilter("leisure", "golf_course");
			wp.addTagFilter("leisure", "nature_reserve");
			wp.addTagFilter("leisure", "park");
			wp.addTagFilter("leisure", "pitch");
			wp.addTagFilter("natural", "fell");
			wp.addTagFilter("natural", "grassland");
			wp.addTagFilter("natural", "heath");
			wp.addTagFilter("natural", "wood");
			wp.addTagFilter("landuse", "forest");
			wp.addTagFilter("landuse", "vineyard");
			wp.addTagFilter("landuse", "village_green");
			wp.addTagFilter("landuse", "recreation_ground");
			wp.addTagFilter("landuse", "orchard");
			wp.addTagFilter("landuse", "meadow");
			wp.addTagFilter("landuse", "grass");
			wp.addTagFilter("landuse", "greenfield");
			in = FileStreamFactory.getStream();
			List<Way> ways = wp.parseWays();

			printMemoryUsage();
			System.out.println("Ways:" + ways.size());

			dao.saveGreenspaces(ways);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void printMemoryUsage() {
		long mem = Runtime.getRuntime().totalMemory()
				- Runtime.getRuntime().freeMemory();
		System.out.println("Memory Usage: " + (mem / 1048576) + "MiB");
	}
}
