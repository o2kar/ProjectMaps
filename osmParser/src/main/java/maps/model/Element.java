package maps.model;

import java.util.HashMap;
import java.util.Map;

public abstract class Element {

	
	protected Map<String, String> tags;

	public Element() {
		super();
		tags = new HashMap<String, String>();
	}

	/**
	 * @param key
	 * @return
	 * @see java.util.Map#get(java.lang.Object)
	 */
	public final String getTag(Object key) {
		return tags.get(key);
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	public final String putTag(String key, String value) {
		return tags.put(key, value);
	}
}
