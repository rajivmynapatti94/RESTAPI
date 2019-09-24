package RESTAPIs_LibraryAPIs;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import envPackage.ResponseConverter;
import envPackage.payLoad;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class AddBook {

		@Test
	public void addBookToLibrary() {

		RestAssured.baseURI = "http://216.10.245.166";

		//Added a new book in our library
		Response res = given()
				.header("Content-Type", "application/json" )
				.body(payLoad.addBook("Horror", "2011")).log().all()
				.when()
				.post("/Library/Addbook.php/json")
				.then().log().all()
				.assertThat()
				.statusCode(200)
				.and()
				.contentType(ContentType.JSON)
				.extract().response();

		JsonPath js = ResponseConverter.jsPath(res);
			String id = js.getString("ID");

		//System.out.println("The Added Book id is " + id);
		
//Delete the added book in the library	
		Response deletestatus = given().
		body("{\n" + 
				"    \"ID\": \""+id+"\"\n" + 
				"}").
		when().
		post("/Library/DeleteBook.php/json").
		then().
		assertThat().statusCode(200).and().contentType(ContentType.JSON).
		extract().response();
		
		JsonPath path = ResponseConverter.jsPath(deletestatus);
		
		String status = path.getString("msg");
		
		System.out.println("Status of delete post is " + status);
		
		

	}

}
