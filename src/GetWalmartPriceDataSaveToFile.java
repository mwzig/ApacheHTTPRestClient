
//here is how to read it from a file
//https://www.mkyong.com/java/json-simple-example-read-and-write-json/
import java.io.BufferedReader;
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
 * A simple Java REST GET example using the Apache HTTP library. This executes a
 * call against the Yahoo Weather API service, which is actually an RSS service
 * (http://developer.yahoo.com/weather/).
 * 
 * Try this Twitter API URL for another example (it returns JSON results):
 * http://search.twitter.com/search.json?q=%40apple (see this url for more
 * twitter info: https://dev.twitter.com/docs/using-search)
 * 
 * Apache HttpClient: http://hc.apache.org/httpclient-3.x/
 *
 */
public class GetWalmartPriceDataSaveToFile {

	public static void main(String[] args) {

		String inputFileName = "C:/Users/WeCanCodeIT/wcci/default-workspace/ApacheHTTPRestClient/src/testinputidfile.txt";
		ArrayList<String> productIdList = new ArrayList<String>();

		try {
			FileReader fr = new FileReader(inputFileName);
			BufferedReader br = new BufferedReader(fr);
			String s;
			while ((s = br.readLine()) != null) {
				productIdList.add(s);
				System.out.println(s);
			}
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String url = "http://www.google.com/search?q=httpClient";
		String url2 = "http://api.walmartlabs.com/v1/items/16213260?format=json&apiKey=r8tk9fjzba6cekkc65qp69xy";
		String url3PartA = "http://api.walmartlabs.com/v1/items/";
		String url3PartB = "?format=json&apiKey=r8tk9fjzba6cekkc65qp69xy";

		for (String productId : productIdList) {
			String urlWithProductId = url3PartA + productId + url3PartB;
			callWalmartProductAPI(urlWithProductId, productId);
		}

	}

	public static void callWalmartProductAPI(String url, String productId) {
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
			// parsing JSON
		}
		JSONParser parser = new JSONParser();

		Object obj = null;
		try {
			// obj = parser.parse(EntityUtils.toString(entity));
			obj = parser.parse(retSrc);
			((JSONObject) obj).put("groceryAppTagId", "001");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (org.json.simple.parser.ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			String outputFileNamePartA = "C:/Users/WeCanCodeIT/wcci/default-workspace/ApacheHTTPRestClient/src/wmpriceitem";
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
