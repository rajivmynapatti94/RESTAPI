package HolofyAPIs;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import Product_APIs_payload.Product;
import Product_APIs_resources.ProductResources;
import Space_APIs_payload.Space;
import Space_APIs_resources.SpaceResources;
import Users_APIs_resources.UserResources;
import envPackage.ResponseConverter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

	public class ReuseableMethod {
		
		private static Logger log = LogManager.getLogger(ReuseableMethod.class.getName());
	
	public static String getAuthorization() throws IOException {
		String bodydata = GenerateStringFromResource(System.getProperty("user.dir")+"/src/main/java/User_APIs_payload/AppLogin.json");
		Response res = given().header("Content-Type", "application/json")
				.body(bodydata)
				.when()
				.post(UserResources.dologin()).

				then().assertThat().statusCode(200).and().contentType(ContentType.JSON)
				.extract().response();

		JsonObject js = ResponseConverter.jsobject(res);
		
		String auth = js.get("authorization").getAsString();
		
		return auth;
	}
	
	public static String getSpaceType() throws IOException {
		
		Response resp = given().header("Content-Type", "application/json")
				.header("authorization", ReuseableMethod.getAuthorization()).when().get(SpaceResources.GetSpaceList()).then()
				.assertThat().statusCode(200).and().contentType(ContentType.JSON).extract().response();

		JsonObject js = ResponseConverter.jsobject(resp);

		// getting the space which consists of array and then looping for object for
		// each index to get the individual index object value for id
		JsonArray ja = js.get("space").getAsJsonArray();

		// int arr = ja.size()

		JsonObject s = (JsonObject) ja.get(0);
		String spaceType1 = s.get("_id").getAsString();
		return spaceType1;
 
	}
	
	public static String createSpaceCard(String auth) throws IOException {
		
		String bodyData = GenerateStringFromResource(System.getProperty("user.dir")+"/src/main/java/Space_APIs_payload/createSpace.json");
		
		Response res2 = given().header("Content-Type", "application/json")
				.header("authorization", auth)
				.body(bodyData)
				.when().post(SpaceResources.NewSpace()).then().assertThat().statusCode(200).and()
				.contentType(ContentType.JSON).extract().response();

		JsonObject js0 = ResponseConverter.jsobject(res2);

		log.info("The response details is : " + js0.get("responseDetails").getAsString());

		JsonArray ja = js0.get("data").getAsJsonArray();

		JsonObject s = (JsonObject) ja.get(0);
		String spacecardid = s.get("_id").getAsString();

		log.info("Newly Created SpaceCard id is " + spacecardid);
		log.info("A new Space has been created successfully");
		
		return spacecardid;
		
}
	
	public static String createProduct(String auth, String spacecardid) {
		
		Response res3 = given().header("Content-Type", "application/json").header("authorization", auth)
				.body(Product.AddProduct("UpdateProductTitle", "UpdateProductDescription", "5cfa540bc7779014978de2bf", spacecardid, "5cd45444e119f26c39a55eea", "5cfa5407c7779014978de2be", "5cd454d7e119f26c39a55eeb",
						"5c8aa18b0605dc668d0f1003", "Smart Devices"))
				.when().post(ProductResources.createProduct()).then().assertThat().statusCode(200).and()
				.contentType(ContentType.JSON).extract().response();

		JsonObject js1 = ResponseConverter.jsobject(res3);

		log.info("The response details is : " + js1.get("responseDetails").getAsString());

		JsonArray product = js1.get("product").getAsJsonArray();

		JsonObject id = (JsonObject) product.get(0);
		String productid = id.get("_id").getAsString();

		log.info("Added product id to the space is : " + productid);
		return productid;
		
	}
	
	public static void deleteSpace(String auth, String spacecardId) {
		
		Response res3 = given().header("Content-Type", "application/json").header("authorization", auth)
				.body(Space.deleteMySpace(spacecardId)).when().delete(SpaceResources.deleteSpace()).

				then().assertThat().statusCode(200).and().contentType(ContentType.JSON).extract()
				.response();

		JsonObject j = ResponseConverter.jsobject(res3);

		log.info("The response details is : " + j.get("responseDetails").getAsString());

		log.info("Space removed successfully with the space card id : " + spacecardId);

	}
	
	public static String GenerateStringFromResource(String path) throws IOException {
		return new String(Files.readAllBytes(Paths.get(path)));
	}

}
