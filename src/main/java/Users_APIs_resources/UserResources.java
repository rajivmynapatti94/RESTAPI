package Users_APIs_resources;

public class UserResources {
	
	public static String dologin() {
		String post = "/v1/user/applogin";
		return post;
	}
	
	public static String forgotPasswordWithNumber() {
		
		String post = "/v1/user/forgotPassword";
		return post;
	}
	
public static String forgotPasswordWithEmail() {
		
		String post = "/v1/user/forgotPassword";
		return post;
	}

}
