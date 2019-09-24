package RESTAPIs;

import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import static org.hamcrest.Matchers.equalTo;


import static io.restassured.RestAssured.given;

public class REST_POST_JSON {

	@Test
	public void postMethod() {

		String bodyData = "{\n" + "    \"location\": {\n" + "        \"lat\": -38.383494,\n"
				+ "        \"lng\": 33.427362\n" + "    },\n" + "    \"accuracy\": 50,\n"
				+ "    \"name\": \"frontline house\",\n" + "    \"phone_number\": \"(+91) 999 999 9999\",\n"
				+ "    \"address\": \"29, side layout, cohen 09\",\n" + "    \"types\": [\n"
				+ "        \"shoe park\",\n" + "        \"shop\"\n" + "    ],\n"
				+ "    \"website\": \"https://google.com\",\n" + "    \"language\": \"French-IN\"\n" + "}";

		RestAssured.baseURI = "http://216.10.245.166";

		given().queryParam("key", "qaclick123").body(bodyData).when().post("maps/api/place/add/json").

				then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and()
				.body("status", equalTo("OK")).and().header("Server", "Apache");

		System.out.println("Post Request completed succesfully");

	}

}
