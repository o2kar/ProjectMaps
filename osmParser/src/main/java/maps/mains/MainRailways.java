package maps.mains;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import maps.daos.RailWayDao;
import maps.daos.WayDao;
import maps.helper.FileStreamFactory;
import maps.model.Node;
import maps.model.Way;
import maps.parser.NodeParser;
import maps.parser.WayParser;

public class MainRailways {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		FileInputStream in;
		try {
			RailWayDao dao = new RailWayDao();
			in = FileStreamFactory.getStream();
			HashMap<String, Node> nodeMap = new NodeParser(in, 48.97, 49.05, 8.33, 8.49).parseNodes();
			in.close();
			
			printMemoryUsage();
			
			WayParser wp = new WayParser(in, nodeMap);
			wp.addTagFilter("railway", ".*");
			in = FileStreamFactory.getStream();
			List<Way> ways = wp.parseWays();

			printMemoryUsage();
			System.out.println("Ways:"+ways.size());
			/*
			 * for (Way way : ways) { System.out.println(way.getName() + " " +
			 * way.getNodes().size()); }
			 */

			
			dao.saveRailways(ways);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void printMemoryUsage(){
		long mem = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
		System.out.println("Memory Usage: "+(mem/1048576)+"MiB");
	}
}
