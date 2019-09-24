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

import Product_APIs_payload.Product;
import Product_APIs_resources.ProductResources;
import envPackage.ResponseConverter;
import envPackage.dataProviderSets;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class AddProductToSpace {

	Properties prop = new Properties();
	private static Logger log = LogManager.getLogger(AddProductToSpace.class.getName());

	@BeforeSuite
	public void getBaseURI() throws IOException {

		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/env.properties");

		prop.load(fis);

	}

	@Test(dataProviderClass = dataProviderSets.class, dataProvider = "addProduct")
	public void AddProductToSpaceAPI(String title, String description, String wallet, String productId, String UserId,
			String productTemplate, String CatId, String CatTitle, int Code) throws IOException {

		RestAssured.baseURI = prop.getProperty("baseURI");

		// -------------------------------Login--------------------------------------------------------//
		String auth = ReuseableMethod.getAuthorization();

		// -------------------------------CreateSpace--------------------------------------------------------//
		String spacecardid = ReuseableMethod.createSpaceCard(auth);

		// -------------------------------Add Product to
		// Space--------------------------------------------------------//
		Response res = null;
		try {
			Response res3 = given().header("Content-Type", "application/json").header("authorization", auth)
					.body(Product.AddProduct(title, description, wallet, spacecardid, productId, UserId,
							productTemplate, CatId, CatTitle))
					.when().post(ProductResources.createProduct());
			res3.then().extract().response();
			res = res3.then().assertThat().statusCode(Code).and().contentType(ContentType.JSON).extract().response();

			JsonObject js1 = ResponseConverter.jsobject(res3);

			log.info("The response details is : " + js1.get("responseDetails").getAsString());

			JsonArray product = js1.get("product").getAsJsonArray();

			JsonObject id = (JsonObject) product.get(0);
			String productid = id.get("_id").getAsString();

			log.info("Added product id to the space is : " + productid);
		}

		catch (AssertionError e) {
			JsonObject js1 = ResponseConverter.jsobject(res);

			log.info("The response details is : " + js1.get("responseDetails").getAsString());

			JsonArray product = js1.get("product").getAsJsonArray();

			JsonObject id = (JsonObject) product.get(0);
			String productid = id.get("_id").getAsString();

			log.info("Added product id to the space is : " + productid);
			throw e;

		}

		// --------------------Delete space ---------------------------------------

		ReuseableMethod.deleteSpace(auth, spacecardid);

	}

}
