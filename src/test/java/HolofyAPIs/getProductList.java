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

import Product_APIs_resources.ProductResources;
import envPackage.ResponseConverter;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class getProductList {
	
	private static Logger log = LogManager.getLogger(getProductList.class.getName());
	Properties prop = new Properties();

	@BeforeSuite
	public void getBaseURI() throws IOException {

		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/env.properties");

		prop.load(fis);

	}
	
	@Test
	public void Productlist() throws IOException {
		
		RestAssured.baseURI = prop.getProperty("baseURI");

		Response resp = given().header("Content-Type", "application/json")
				.header("authorization", ReuseableMethod.getAuthorization()).when().get(ProductResources.getProductList()).then()
				.assertThat().statusCode(200).and().contentType(ContentType.JSON).extract().response();

		JsonObject js = ResponseConverter.jsobject(resp);

		JsonArray ja = js.get("data").getAsJsonArray();
		
		//System.out.println(ja.size());
		
		JsonObject data0 = (JsonObject) ja.get(0);
		 
		//System.out.println("Data 0 array is : " + data0.get("_id").getAsString());
		
		JsonArray producttemplates  = data0.get("producttemplate").getAsJsonArray();
		
		//System.out.println(producttemplates.size());
		
		JsonObject Products = (JsonObject) producttemplates.get(0);
		
		log.info("The List of the product are available within the index 1 : " + Products);
		
		//System.out.println(templates.get("title").getAsString());
		//System.out.println(templates.get("product").getAsString());
		//System.out.println(templates0.get("CatTitle").getAsString());
		//System.out.println(templates0.get("CatId").getAsString());
		
		
		
	}

}
