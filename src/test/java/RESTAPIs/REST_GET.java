package RESTAPIs;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class REST_GET {

	// public static void main(String[] args) {
	@Test
	public void GetMethod() {

		// http://www.ishandevshukl.com/2018/09/how-to-solve-exception-in-thread-main.html
		// - resolved the below issues
		// getting error as "Caused by: java.lang.ClassNotFoundException:
		// io.restassured.RestAssured"

		// https://stackoverflow.com/questions/41984727/java-lang-classnotfoundexception-org-hamcrest-matchers-after-adding-dependency
		// Caused by: java.lang.ClassNotFoundException: org.hamcrest.Matcher

		RestAssured.baseURI = "https://maps.googleapis.com";

		given().param("location", "-33.8670522,151.1957362").param("radius", "1500").param("type", "restaurant")
				.param("keyword", "cruise").param("key", "AIzaSyD1ufX1a5pt6wWusfU4BW6DXq4SSvaZLmI").

				when().get("/maps/api/place/nearbysearch/json").

				then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and()
				.body("results[0].vicinity",
						equalTo("Level 1, 2 and 3, Overseas Passenger Terminal, Circular Quay W, The Rocks"))
				.and().header("server", "scaffolding on HTTPServer2");
		System.out.println("You are welcome");

	}

}
