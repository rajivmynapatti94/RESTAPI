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

import Space_APIs_payload.Space;
import Space_APIs_resources.SpaceResources;
import envPackage.ResponseConverter;
import envPackage.dataProviderSets;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreateSpace {
	
	Properties prop = new Properties();
	private static Logger log = LogManager.getLogger(CreateSpace.class.getName());
	
	@BeforeSuite
	public void getBaseURI() throws IOException {

		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/env.properties");

		prop.load(fis);

	}

	@Test(dataProviderClass = dataProviderSets.class, dataProvider = "Create-New-Space")
	public void createNewSpaceAPI(String bedroomCount, String sleepsCount, String title, String description, int Code) throws IOException {

		RestAssured.baseURI = prop.getProperty("baseURI");
		
		
		//--------------Login----------------------------------------
		
		String auth = ReuseableMethod.getAuthorization();
		
		//-------------------------------CreateSpace-------------------------------------------------------//
			Response res= null;
		try {
		Response response = given().header("Content-Type", "application/json")
				.header("authorization", auth)
				.body(Space.appCreateSpace(bedroomCount, sleepsCount, "5c9d0538c4a9a46122bdf0c2", title, description))
				.when().post(SpaceResources.NewSpace());
		res = response.then().extract().response();
		response.then().assertThat().statusCode(Code).and()
				.contentType(ContentType.JSON);

		JsonObject js = ResponseConverter.jsobject(res);

		log.info("The response details is : " + js.get("responseDetails").getAsString());

		JsonArray ja = js.get("data").getAsJsonArray();

		JsonObject s = (JsonObject) ja.get(0);
		String spacecardid = s.get("_id").getAsString();

		log.info(spacecardid);
		
		//-------------------------------DeleteSpace-------------------------------------------------------//
				ReuseableMethod.deleteSpace(auth, spacecardid);
		}
		catch(AssertionError e) {
			JsonObject js = ResponseConverter.jsobject(res);

			log.info("The response details is : " + js.get("responseDetails").getAsString());

			JsonArray ja = js.get("data").getAsJsonArray();

			JsonObject s = (JsonObject) ja.get(0);
			String spacecardid = s.get("_id").getAsString();

			log.info(spacecardid);
			
			//-------------------------------DeleteSpace-------------------------------------------------------//
			ReuseableMethod.deleteSpace(auth, spacecardid);
			
			throw e;
			
		}

	}

}
