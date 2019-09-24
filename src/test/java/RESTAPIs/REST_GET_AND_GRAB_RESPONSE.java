package RESTAPIs;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import envPackage.Resources;
import envPackage.ResponseConverter;
import envPackage.payLoad;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class REST_GET_AND_GRAB_RESPONSE {
	Properties prop = new Properties();
	
	
	@BeforeTest
	public void getProperties() throws IOException {
	
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"/src/main/java/envPackage/env.properties");
		prop.load(fis);
	}
	
	@Test
	public void addAndDeletePlace() {
		
		RestAssured.baseURI=prop.getProperty("HOST");
// Task 1: Got the Response in res using the extract().response()	
		Response res = given().
		queryParam("key", prop.getProperty("KEY")).
		body(payLoad.postBody()).
		when().
		post(Resources.postResources()).
		then().
		assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
		body("status", equalTo("OK")).and().
		header("Server", "Apache").
		extract().response();
		
		System.out.println("A New Place has been created successfully");
		
//Task 2: Converted the Raw data to String and then Converted that String to JSON using JSON Path
		JsonPath js=ResponseConverter.jsPath(res);
		String jsdata =	js.prettify();
		
		System.out.println("Add place Json data is: "+"\n"+"" +jsdata);
		String placedId= js.get("place_id");
		
		System.out.println("Generated place ID is : " +placedId);
		
// Task 3: Get the Placeid and add it in the body for delete post method
		
		given().
		queryParam("key", prop.getProperty("KEY")).
		body("{\n" + 
				"    \"place_id\": \"" +placedId+"\"\n" + 
				"}").
		
		when().
		post(Resources.deleteResources()).
		
		then().
		assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
		body("status", equalTo("OK"));
		
		System.out.println("Created placed is deleted successfully");
	}


}
