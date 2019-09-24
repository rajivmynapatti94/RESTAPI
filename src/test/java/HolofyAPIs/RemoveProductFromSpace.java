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

import Product_APIs_payload.Product;
import Product_APIs_resources.ProductResources;
import envPackage.ResponseConverter;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class RemoveProductFromSpace {

	private static Logger log = LogManager.getLogger(RemoveProductFromSpace.class.getName());
	Properties prop = new Properties();

	@BeforeSuite
	public void getBaseURI() throws IOException {

		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/env.properties");

		prop.load(fis);

	}

	@Test
	public void removeAddedProductAPI() throws IOException {

		RestAssured.baseURI = prop.getProperty("baseURI");

		// ------------------Do Login --------------------

		String auth = ReuseableMethod.getAuthorization();

		// -----------------Create Space -------------------

		String spacecardId = ReuseableMethod.createSpaceCard(auth);

		// -------------------Add Product-----------------------

		String productid = ReuseableMethod.createProduct(auth, spacecardId);

		// ------------------RemoveProduct-----------------------
		Response res = null;

		try {
			Response response = given().header("Content-Type", "application/json").header("authorization", auth).

					body(Product.RemoveProduct(spacecardId, productid)).

					when().delete(ProductResources.deleteProduct());
			res = response.then().extract().response();
			response.then().assertThat().statusCode(200).and().contentType(ContentType.JSON);

			JsonObject js = ResponseConverter.jsobject(res);

			log.info("The response details is : " + js.get("responseDetails").getAsString());

			// --------------------------Delete space --------------------------

			ReuseableMethod.deleteSpace(auth, spacecardId);

		} catch (AssertionError e) {

			JsonObject js = ResponseConverter.jsobject(res);

			log.info("The response details is : " + js.get("responseDetails").getAsString());

			// --------------------------Delete space --------------------------

			ReuseableMethod.deleteSpace(auth, spacecardId);

			throw e;
		}

	}
}
