
//here is how to read it from a file
//https://www.mkyong.com/java/json-simple-example-read-and-write-json/
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * The initial logic was copied from a StackOverflow site search for Apache
 * HttpClient: http://hc.apache.org/httpclient-3.x/
 *
 * This code reads in a file that has comma separated values xxx,xxxxxxxx,xxx
 * The characters before the 1st comma are the groceryApp tag id. The characters
 * between the commas are the Walmart ItemId The characters after the 2nd comma
 * are the price that we find manually if we know that Walmart's json data does
 * not contain a price
 *
 */
public class GetWalmartPriceDataSaveToFile {

	public static void main(String[] args) {

		String basePath = new File("").getAbsolutePath();
		basePath += "/Resources";
		System.out.println(basePath);

		String inputFileName = "C:/Users/WeCanCodeIT/wcci/default-workspace/ApacheHTTPRestClient/src/testinputidfile.txt";
		ArrayList<String> tagProductIdPriceList = new ArrayList<String>();

		try {
			FileReader fr = new FileReader(inputFileName);
			BufferedReader br = new BufferedReader(fr);
			String s;
			while ((s = br.readLine()) != null) {
				tagProductIdPriceList.add(s);
				System.out.println(s);
			}
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// String url2 =
		// "http://api.walmartlabs.com/v1/items/16213260?format=json&apiKey=r8tk9fjzba6cekkc65qp69xy";
		String urlPartA = "http://api.walmartlabs.com/v1/items/";
		String urlPartB = "?format=json&apiKey=r8tk9fjzba6cekkc65qp69xy";

		for (String tagProductIdPrice : tagProductIdPriceList) {
			int firstCommaLoc = tagProductIdPrice.indexOf(',');
			int secondCommaLoc = tagProductIdPrice.indexOf(',', firstCommaLoc + 1);
			String tag = tagProductIdPrice.substring(0, firstCommaLoc);
			String productId = tagProductIdPrice.substring(firstCommaLoc + 1, secondCommaLoc);
			String price = tagProductIdPrice.substring(secondCommaLoc + 1);
			String urlWithProductId = urlPartA + productId + urlPartB;
			callWalmartProductAPI(urlWithProductId, productId, tag, price, basePath);
		}

	}

	public static void callWalmartProductAPI(String url, String productId, String tag, String price, String basePath) {

		HttpClient httpclient = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);

		HttpResponse response = null;
		try {
			response = httpclient.execute(request);
		} catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
		HttpEntity entity = response.getEntity();

		String retSrc = null;
		if (entity != null) {
			try {
				retSrc = EntityUtils.toString(entity);
				System.out.println(retSrc);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		JSONParser parser = new JSONParser();

		Object obj = null;
		try {
			obj = parser.parse(retSrc);
			((JSONObject) obj).put("groceryAppTagId", tag);
			((JSONObject) obj).put("groceryAppPrice", price);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (org.json.simple.parser.ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			String outputFileNamePartA = basePath + "/wmpriceitem";
			String outputFileNamePartB = ".txt";
			String outputFileName = outputFileNamePartA + productId + outputFileNamePartB;
			FileWriter file = new FileWriter(outputFileName);
			file.write(obj.toString());
			file.flush();
			System.out.println("Successfully Copied JSON Object to File...");
			System.out.println("\nJSON Object: " + obj);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	/*
	 * JSONObject jsonObject = (JSONObject) obj; System.out.println(jsonObject);
	 * 
	 * String name = (String) jsonObject.get("name"); System.out.println(name);
	 * 
	 * System.out.println("----------------------------------------");
	 * System.out.println(response.getStatusLine()); Header[] headers =
	 * response.getAllHeaders(); for (int i = 0; i < headers.length; i++) {
	 * System.out.println(headers[i]); }
	 * System.out.println("----------------------------------------");
	 */
}
