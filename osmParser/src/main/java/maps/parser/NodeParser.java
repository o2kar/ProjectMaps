package maps.parser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import maps.helper.Pair;
import maps.model.Element;
import maps.model.Node;

/**
 * The NodeParser extracts all Nodes out of an OSM XML file
 * @author rroesch
 *
 */
public class NodeParser extends ElementParser {

	private double latMin;
	private double latMax;
	private double lonMin;
	private double lonMax;

	/**
	 * 
	 * @param in InputStream which delivers an OSM-XML
	 * @param latMin The minimum latitude
	 * @param latMax The maximum latitude
	 * @param lonMin The minimum longitude
	 * @param lonMax The maximum latitude
	 */
	public NodeParser(InputStream in, double latMin, double latMax,
			double lonMin, double lonMax) {
		super(in);
		this.latMin = latMin;
		this.latMax = latMax;
		this.lonMin = lonMin;
		this.lonMax = lonMax;
	}

	/**
	 * Parses the hole XML file
	 * @return A Map of Nodes, with the IDs as key
	 */
	public HashMap<String, Node> parseNodes() {
		HashMap<String, Node> nodeMap = new HashMap<String, Node>();

		try {
			XMLStreamReader rdr = XMLInputFactory.newInstance()
					.createXMLStreamReader(in);

			while (rdr.hasNext()) {
				switch (rdr.next()) {
				case XMLStreamConstants.START_ELEMENT:
					if (rdr.getLocalName().equalsIgnoreCase("node")) {
						double lat = 0;
						double lon = 0;
						String id = null;
						HashMap<String, String> tags = new HashMap<String, String>();

						// get Attributes
						for (int i = 0; i < rdr.getAttributeCount(); i++) {
							if (rdr.getAttributeLocalName(i).equalsIgnoreCase(
									"lat")) {
								lat = Double.valueOf(rdr.getAttributeValue(i));
							}
							if (rdr.getAttributeLocalName(i).equalsIgnoreCase(
									"lon")) {
								lon = Double.valueOf(rdr.getAttributeValue(i));
							}
							if (rdr.getAttributeLocalName(i).equalsIgnoreCase(
									"id")) {
								id = rdr.getAttributeValue(i);
							}
						}

						// get containing Elements
						while (rdr.hasNext()
								&& !(rdr.next() == XMLStreamConstants.END_ELEMENT && rdr
										.getLocalName()
										.equalsIgnoreCase("node"))) {

							switch (rdr.getEventType()) {
							case XMLStreamConstants.START_ELEMENT:
								if (rdr.getLocalName().equalsIgnoreCase("tag")) {
									Pair<String, String> tag = this
											.parseTag(rdr);
									tags.put(tag.getVar1(), tag.getVar2());
									break;
								}
							}
						}

						if (lat >= latMin && lat <= latMax && lon >= lonMin
								&& lon <= latMax) {
							if (!nodeMap.containsKey(id)) {
								Node nd = new Node(lon, lat, tags);
								if (this.isElementSearched(nd)) {
									nodeMap.put(id, nd);
								}

							} else {
								System.out.println("Dup! " + id);
							}
						}

						break;
					}
				}
			}
			in.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return nodeMap;
	}

}
