package envPackage;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class ResponseConverter {
	
	public static JsonPath jsPath(Response r) {
		
		String stringConvertedData= r.asString();
		JsonPath js = new JsonPath(stringConvertedData);
		return js;
	}
	
	public static XmlPath xmlPath (Response r) {
		
		String stringConvertedData= r.asString();
		XmlPath js = new XmlPath(stringConvertedData);
		return js;
	}
	
	public static JsonObject jsobject(Response r) {
		String raw = r.asString();
		JsonObject jsonObject = new JsonParser().parse(raw).getAsJsonObject();
		JsonObject data =  new JsonParser().parse(jsonObject.get("data").getAsString()).getAsJsonObject();
		return data;
		
		
	}
	
	
}
