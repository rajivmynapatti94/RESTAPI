package RESTAPIs;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class REST_POST_XML {

	@Test
	public void xmlpostMethod() throws IOException {

		String bodyData = GenerateStringFromResource("/Users/indianic/Downloads/post.xml");

		RestAssured.baseURI = "http://216.10.245.166";

		Response res = given().queryParam("key", "qaclick123").body(bodyData).

				when().post("maps/api/place/add/xml").

				then().assertThat().statusCode(200).extract().response();
		// .and().contentType(ContentType.XML);
		// .and().
		// body("status", equalTo("OK")).and().
		// header("Server", "Apache");
		//
		String output = res.asString();
		System.out.println(output);
		System.out.println("Post Request completed succesfully");

	}

	public static String GenerateStringFromResource(String path) throws IOException {
		return new String(Files.readAllBytes(Paths.get(path)));
	}

}
