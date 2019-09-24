package User_APIs_payload;

public class User {
	
	public static String appLogin(String Number, String password, String Countrycode, boolean isNullPresent) {
		String applogin = null;
		if(isNullPresent) {
			if (Number == null) {
				applogin = "{\n" + "    \"phoneNumber\": null,\n" + "    \"password\": \"" + password
				+ "\",\n" + "    \"countryCode\": \"" + Countrycode + "\",\n" + "    \"deviceToken\": \"ABCDEFG\"\n"
				+ "}";
			} else if(password == null) {
				applogin = "{\n" + "    \"phoneNumber\": \"" + Number + "\",\n" + "    \"password\": null ,\n" + "    \"countryCode\": \"" + Countrycode + "\",\n" + "    \"deviceToken\": \"ABCDEFG\"\n"
						+ "}";
			} else if(Countrycode == null) {
				applogin = "{\n" + "    \"phoneNumber\": \"" + Number + "\",\n" + "    \"password\": \"" + password
						+ "\",\n" + "    \"countryCode\": null,\n" + "    \"deviceToken\": \"ABCDEFG\"\n"
						+ "}";
			}
		} else {
			applogin = "{\n" + "    \"phoneNumber\": \"" + Number + "\",\n" + "    \"password\": \"" + password
					+ "\",\n" + "    \"countryCode\": \"" + Countrycode + "\",\n" + "    \"deviceToken\": \"ABCDEFG\"\n"
					+ "}";
		}
		
		return applogin;
	}
	
	public static String appTestLogin(String password, String Countrycode) {

		String applogin = "{\n" + "    \"password\": \"" + password
				+ "\",\n" + "    \"countryCode\": \"" + Countrycode + "\",\n" + "    \"deviceToken\": \"ABCDEFG\"\n"
				+ "}";
		return applogin;
	}
	
	
	public static String appForgotPasswordWithNumber(String Number, String Countrycode) {

		String appforgot = "{\n" + "    \"phoneNumber\": \"" + Number + "\",\n" + "    \"countryCode\": \""
				+ Countrycode + "\"\n" + "}";
		return appforgot;
	}
	
	public static String appForgotPasswordWithEmail(String email) {

		String appforgot = "{\n" + "    \"email\": \"" + email + "\"\n" + "}";
		return appforgot;

	}

}
