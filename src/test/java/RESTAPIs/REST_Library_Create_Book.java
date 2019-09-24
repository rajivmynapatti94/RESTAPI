package RESTAPIs;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class REST_Library_Create_Book {

	@Test
	public void addBook() {

		String addBookPayload = "{\n" + "    \"name\": \"Man Vs Wild\",\n" + "    \"isbn\": \"Nature\",\n"
				+ "    \"aisle\": \"200\",\n" + "    \"author\": \"Bear Grylls\"\n" + "}";

		RestAssured.baseURI = "http://216.10.245.166";

		Response res = given().body(addBookPayload).

				when().post("Library/Addbook.php/json").

				then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and()
				.body("ID", equalTo("Nature200")).extract().response();
		System.out.println("A new book successfully added");

		String rawBodyOutput = res.asString();

		JsonPath js = new JsonPath(rawBodyOutput);

		String jsdata = js.prettify();

		System.out.println(jsdata);

		// System.out.println(js.get("ID"));

	}

}
