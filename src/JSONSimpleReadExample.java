
//https://www.mkyong.com/java/json-simple-example-read-and-write-json/
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONSimpleReadExample {

	public static void main(String[] args) {

		JSONParser parser = new JSONParser();

		try {

			Object obj = parser.parse(new FileReader(
					"C:/Users/WeCanCodeIT/wcci/default-workspace/ApacheHTTPRestClient/src/16213260.txt"));

			JSONObject jsonObject = (JSONObject) obj;
			System.out.println(jsonObject);

			String name = (String) jsonObject.get("name");
			// storeItem.setName(name); set the class member variable from the JSON
			System.out.println(name);

			String shortDesc = (String) jsonObject.get("shortDescription");
			System.out.println(shortDesc);

			String longDesc = (String) jsonObject.get("longDescription");
			System.out.println(longDesc);

			Float salePrice = (Float) jsonObject.get("salePrice");
			System.out.println(salePrice);

			// loop array
			JSONArray msg = (JSONArray) jsonObject.get("messages");
			Iterator<String> iterator = msg.iterator();
			while (iterator.hasNext()) {
				System.out.println(iterator.next());
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

}