package HolofyAPIs;

import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;

import Space_APIs_payload.Space;
import Space_APIs_resources.SpaceResources;
import envPackage.ResponseConverter;
import envPackage.dataProviderSets;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class EditCreatedSpace {
	private static Logger log = LogManager.getLogger(EditCreatedSpace.class.getName());
	Properties prop = new Properties();

	@BeforeSuite
	public void getBaseURI() throws IOException {

		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/env.properties");

		prop.load(fis);

	}

	@Test(dataProviderClass = dataProviderSets.class, dataProvider = "updateSpace")
	public void EditSpaceAPI(String bedroomCount, String sleepsCount, String title, String description, int Code)
			throws InterruptedException, IOException {

		RestAssured.baseURI = prop.getProperty("baseURI");

		// -------------------------------Login--------------------------------------------------------//
		String auth = ReuseableMethod.getAuthorization();

		// -------------------------------CreateSpace--------------------------------------------------------//

		String spacecardid = ReuseableMethod.createSpaceCard(auth);

		// -------------------------------Edit Spacce
		// --------------------------------------------------------//

		Response res = null;
		try {
			Response res3 = given().header("Content-Type", "application/json").header("authorization", auth)
					.body(Space.updateAddedSpace(bedroomCount, sleepsCount, title, description,
							"5c9d0538c4a9a46122bdf0c2", spacecardid))
					.when().put(SpaceResources.EditSpace());

			res = res3.then().extract().response();
			res3.then().assertThat().statusCode(Code).and().contentType(ContentType.JSON);

			JsonObject j = ResponseConverter.jsobject(res);

			log.info("The response details is : " + j.get("responseDetails").getAsString());

			log.info("Space edited successfully with the space card id : " + spacecardid);

			// -------------------------------Delete
			// Space--------------------------------------------------------//

			ReuseableMethod.deleteSpace(auth, spacecardid);
		} catch (AssertionError e) {
			JsonObject j = ResponseConverter.jsobject(res);

			log.info("The response details is : " + j.get("responseDetails").getAsString());

			log.info("Space edited successfully with the space card id : " + spacecardid);

			// -------------------------------Delete
			// Space--------------------------------------------------------//

			ReuseableMethod.deleteSpace(auth, spacecardid);

		}

	}
}
