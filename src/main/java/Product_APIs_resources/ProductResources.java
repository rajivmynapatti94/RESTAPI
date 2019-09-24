package Product_APIs_resources;

public class ProductResources {
	
	public static String createProduct() {
		
		String post = "/v1/product/createProduct";
		
		return post;
	}
	
	public static String getProductList() {
		
		String get = "/v1/product/productList";
		
		return get;
	}
	
	public static String editProduct() {
		
		String put = "/v1/product/updateProduct ";
		
		return put;
	}
	
public static String deleteProduct() {
		
		String delete = "/v1/product/removeProduct";
		
		return delete;
	}

}
