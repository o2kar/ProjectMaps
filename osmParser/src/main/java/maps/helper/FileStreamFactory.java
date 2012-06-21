package maps.helper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FileStreamFactory {

	public static FileInputStream getStream() throws FileNotFoundException {
		String osmFilePath = System.getenv("osmFile");
		System.out.println(osmFilePath);
		if (osmFilePath != null) {
			return new FileInputStream(osmFilePath);
		} else {
			throw new RuntimeException("Enviroment variable for osm file not set. Please set osmFile first.");
		}
	}

}
