package maps.mains;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import maps.helper.FileStreamFactory;
import maps.model.Node;
import maps.parser.NodeParser;

public class TestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		long time = System.currentTimeMillis();
		FileInputStream in = null;
		HashMap<String, Node> nodeMap = null;
		try {
			in = FileStreamFactory.getStream();
			NodeParser np = new NodeParser(in, 48.97, 49.05, 8.33, 8.49);
//			np.addTagFilter("leisure", "common");
//			np.addTagFilter("leisure", "dog_park");
//			np.addTagFilter("leisure", "garden");
//			np.addTagFilter("leisure", "golf_course");
//			np.addTagFilter("leisure", "nature_reserve");
//			np.addTagFilter("leisure", "park");
//			np.addTagFilter("leisure", "pitch");
			//np.addTagFilter("natural", ".*");
			//np.addTagFilter("landuse", ".*");
			nodeMap = np.parseNodes();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (Exception e) {
			}
		}
		System.out.println("Time: " + (System.currentTimeMillis() - time)
				/ (1000) + "s");
		System.out.println("Found "+nodeMap.size()+" nodes");
		long mem = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
		System.out.println("Memory Usage: "+(mem/1048576)+"MiB");
	}

}
