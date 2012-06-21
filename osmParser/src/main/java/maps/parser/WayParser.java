package maps.parser;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import maps.helper.FileStreamFactory;
import maps.helper.Pair;
import maps.model.Node;
import maps.model.Way;

/**
 * The class is for parsing the Ways out of an OSM-XML file
 * 
 * @author rroesch
 * 
 */
public class WayParser extends ElementParser {

	private Map<String, Node> nodeMap;

	/**
	 * 
	 * @param in InputStream which delivers an OSM-XML
	 * @param nodeMap a Map which contains all required Nodes. You can get this with the {@link maps.parser.NodeParser}
	 */
	public WayParser(InputStream in, Map<String, Node> nodeMap) {
		super(in);
		this.nodeMap = nodeMap;
	}

	/**
	 * parses the OSM-XML file
	 * @return a list of all ways
	 */
	public List<Way> parseWays() {
		List<Way> ways = new ArrayList<Way>();

		try {
			in = FileStreamFactory.getStream();
			XMLStreamReader rdr = XMLInputFactory.newInstance()
					.createXMLStreamReader(in);

			while (rdr.hasNext()) {
				switch (rdr.next()) {
				case XMLStreamConstants.START_ELEMENT:
					if (rdr.getLocalName().equalsIgnoreCase("way")) {
						Way way = new Way();
						for (int i = 0; i < rdr.getAttributeCount(); i++) {
							if (rdr.getAttributeLocalName(i).equals("id")) {
								way.setId(rdr.getAttributeValue(i));
							}
						}
						while (rdr.hasNext()
								&& !(rdr.next() == XMLStreamConstants.END_ELEMENT && rdr
										.getLocalName().equalsIgnoreCase("way"))) {
							switch (rdr.getEventType()) {
							case XMLStreamConstants.START_ELEMENT:

								if (rdr.getLocalName().equalsIgnoreCase("nd")) {
									for (int j = 0; j < rdr.getAttributeCount(); j++) {
										if (rdr.getAttributeLocalName(j)
												.equals("ref")) {
											if (nodeMap.containsKey(rdr
													.getAttributeValue(j))) {
												way.addNode(nodeMap.get(rdr
														.getAttributeValue(j)));
											}
										}
									}
								}

								if (rdr.getLocalName().equalsIgnoreCase("tag")) {
									Pair<String, String> tag = this
											.parseTag(rdr);
									if (tag.getVar1() != null
											&& tag.getVar2() != null
											&& tag.getVar1().equals("name")) {
										way.setName(tag.getVar2());
									}
									if (tag.getVar1() != null
											&& tag.getVar2() != null
											&& tag.getVar1().equals("highway")) {
										way.setHighway(tag.getVar2());
									}
									way.putTag(tag.getVar1(), tag.getVar2());

								}

								break;

							default:
								break;
							}
						}
						if (this.isElementSearched(way)) {
							ways.add(way);
						}
					}
					break;

				default:
					break;
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ways;
	}

}
