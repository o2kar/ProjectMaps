package maps.parser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import maps.helper.Pair;
import maps.model.Element;

/**
 * Abstract class for parsing an Element
 * 
 * @author rroesch
 * 
 */
public abstract class ElementParser {

	protected InputStream in;
	private List<TagFilter> tagFilters;

	public ElementParser(InputStream in) {
		super();
		this.in = in;
		this.tagFilters = new ArrayList<ElementParser.TagFilter>();
	}

	protected Pair<String, String> parseTag(XMLStreamReader rdr)
			throws XMLStreamException {
		switch (rdr.getEventType()) {
		case XMLStreamConstants.START_ELEMENT:
			if (rdr.getLocalName().equalsIgnoreCase("tag")) {
				String k = null;
				String v = null;

				for (int i = 0; i < rdr.getAttributeCount(); i++) {
					if (rdr.getAttributeLocalName(i).equals("k")) {
						k = rdr.getAttributeValue(i);
					}
					if (rdr.getAttributeLocalName(i).equals("v")) {
						v = rdr.getAttributeValue(i);
					}
				}
				if (k != null && v != null) {
					return new Pair<String, String>(k, v);
				}
			}
		}
		throw new RuntimeException("Invalid position for a Tag");
	}

	protected boolean isElementSearched(Element element) {
		if (tagFilters.isEmpty()) {
			return true;
		}
		for (TagFilter filter : tagFilters) {
			String v = element.getTag(filter.key);
			if (v != null && v.matches(filter.value)) {
				return true;
			}
		}
		return false;
	}

/**
 * If you add a TagFilter, only these elements will be parsed, which contain a Tag with the given key and value
 * @param key the key
 * @param value the value; this can also be a regex String. => .* if the tag-value doesn't matter.
 * @return
 */
	public boolean addTagFilter(String key, String value) {
		return tagFilters.add(new TagFilter(key, value));
	}

	public class TagFilter {
		protected String key;
		protected String value;

		public TagFilter(String key, String value) {
			super();
			this.key = key;
			this.value = value;
		}
	}
}
