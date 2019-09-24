package envPackage;

	public class payLoad {

	public static String postBody() {

		String Postbody = "{\n" + "    \"location\": {\n" + "        \"lat\": -38.383494,\n"
				+ "        \"lng\": 33.427362\n" + "    },\n" + "    \"accuracy\": 50,\n"
				+ "    \"name\": \"frontline house\",\n" + "    \"phone_number\": \"(+91) 999 999 9999\",\n"
				+ "    \"address\": \"29, side layout, cohen 09\",\n" + "    \"types\": [\n"
				+ "        \"shoe park\",\n" + "        \"shop\"\n" + "    ],\n"
				+ "    \"website\": \"https://google.com\",\n" + "    \"language\": \"French-IN\"\n" + "}";
		return Postbody;
	}

	public static String addBook(String isbn, String aisle) {

		String bookpayload = "{\n" + "    \"name\": \"It happens for a reason\",\n" + "    \"isbn\": \"" + isbn
				+ "\",\n" + "    \"aisle\": \"" + aisle + "\",\n" + "    \"author\": \"Preeti Shenoy\"\n" + "}";

		return bookpayload;
	}

	
}
