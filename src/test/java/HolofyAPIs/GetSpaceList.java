package HolofyAPIs;

import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import Space_APIs_resources.SpaceResources;
import envPackage.ResponseConverter;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetSpaceList {
	
	private static Logger log = LogManager.getLogger(GetSpaceList.class.getName());
	Properties prop = new Properties();

	@BeforeSuite
	public void getBaseURI() throws IOException {

		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/env.properties");

		prop.load(fis);

	}

	@Test
	public void ListOfSpaceType() throws IOException {

		RestAssured.baseURI = prop.getProperty("baseURI");

		Response resp = given().header("Content-Type", "application/json")
				.header("authorization", ReuseableMethod.getAuthorization()).when().get(SpaceResources.GetSpaceList()).then()
				.assertThat().statusCode(200).and().contentType(ContentType.JSON).extract().response();

		JsonObject js = ResponseConverter.jsobject(resp);

		// getting the space which consists of array and then looping for object for
		// each index to get the individual index object value for id
		JsonArray ja = js.get("space").getAsJsonArray();

//		int arr = ja.size();
//
//		for (int i = 0; i < arr; i++) {
//
//			JsonObject s = (JsonObject) ja.get(i);
//			String spaceType1 = s.get("_id").getAsString();
//
//			System.out.println(spaceType1);
//
//		}
		 JsonObject so = (JsonObject) ja.get(0);
		// JsonObject s1 = (JsonObject) ja.get(1);
		// JsonObject s2 = (JsonObject) ja.get(2);
		//
		log.info("Spacetype 1 id is : " + so.get("_id").getAsString());
		// System.out.println("Spacetype 2 id is : " + s1.get("_id").getAsString());
		// System.out.println("Spacetype 3 id is : " + s2.get("_id").getAsString());

	}

}
