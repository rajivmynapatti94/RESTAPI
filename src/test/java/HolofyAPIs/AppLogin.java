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

import User_APIs_payload.User;
import Users_APIs_resources.UserResources;
import envPackage.ResponseConverter;
import envPackage.dataProviderSets;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class AppLogin {

	private static Logger log = LogManager.getLogger(AppLogin.class.getName());

	Properties prop = new Properties();

	@BeforeSuite
	public void getBaseURI() throws IOException {

		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/env.properties");

		prop.load(fis);

	}

	@Test(dataProviderClass = dataProviderSets.class, dataProvider = "login-credentials")
	public void getLoginAPI(String Number, String password, String Countrycode, boolean isNullPresent, int Code) {
		Response res = null;
		try {
			RestAssured.baseURI = prop.getProperty("baseURI");

			Response res1 = given().header("Content-Type", "application/json").body(User.appLogin(Number, password, Countrycode, isNullPresent)).when().post(UserResources.dologin());

			res = res1.then().extract().response();
			res1.then().assertThat().statusCode(Code).and().contentType(ContentType.JSON);

			JsonObject js = ResponseConverter.jsobject(res);

			log.info("The response details is : " + js.get("responseDetails"));
		} catch (AssertionError e) {

			JsonObject js = ResponseConverter.jsobject(res);

			log.info("The response details is : " + js.get("responseDetails"));
			throw e;
		}

		// log.info(js.get("responseDetails"));

	}
}
