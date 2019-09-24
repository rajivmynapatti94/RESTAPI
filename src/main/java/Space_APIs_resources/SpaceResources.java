package Space_APIs_resources;

public class SpaceResources {
	
	public static String GetSpaceList() {
		
		String get = "/v1/space/spaceList";
		return get;
	}
	
	public static String NewSpace() {
		String post = "/v1/space/createSpace";
		return post;
	}
	
	public static String EditSpace() {
		String put = "/v1/space/updateSpace";
		return put;
	}
	
	public static String deleteSpace() {
		
		String delete = "/v1/space/removeSpace";
		
		return delete;
	}

}
