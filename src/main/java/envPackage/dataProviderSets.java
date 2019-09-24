package envPackage;

import org.testng.annotations.DataProvider;

public class dataProviderSets {

	// We are using this dataProviderSets class in dataProvider class by adding
	// Static in the public object[] [] loginData()

	@DataProvider(name = "login-credentials")
	public static Object[][] loginData() {

		return new Object[][] {

				// Valid user login credentials
				{ "9876543212", "qwerty", "+44", false, 200 },

				// Phone number doesn't exist
				{ "1234567890", "test123", "+1", false, 404 },

				// invalid password
				{ "9876543212", "test123", "+44", false, 400 },
					
				// Without phonenumber parameter
				{ null, "test123", "+44", true, 500 }

		};
	}

	@DataProvider(name = "forgot-passwordWithNumber")
	public static Object[][] forgotpasswordData() {

		return new Object[][] {

				// Valid user login credentials
				{ "9876543212", "+44", 200 },

				// Phone number doesn't exist
				{ "1234567890", "+1", 400 },

				// Blank Number and Blank password
				{ "", "", 400 },

				// Email instead of Phone number
				{ "test+12@gmail.com", "test", 400 }

		};
	}

	@DataProvider(name = "forgot-passwordWithEmail")
	public static Object[][] forgotpasswordEmail() {

		return new Object[][] {

				// Valid user login credentials
				{ "test+12@gmail.com", 200 },

				// Phone number doesn't exist
				{ "test@grr.la", 400 },

				// Blank Email id
				{ "", 400 },

				// Passing Phone number instead of Email id
				{ "1234567890", 400 }

		};

	}

	@DataProvider(name = "Create-New-Space")
	public static Object[][] createSpace() {

		return new Object[][] {

				// Bedcount, Sleepcount, SpaceTitle, Space description, Expected Status code
				{ "2", "5", "My New Space From API", "Verifying if the space is getting created or not", 200 },
				{ "", "5", "My New Space From API", "Verifying if the space is getting created or not", 200 } };
	}

	@DataProvider(name = "updateSpace")
	public static Object[][] updateSpace() {

		return new Object[][] {

				{ "11", "12", "Update the Space title through API", "Updated the Space Description through API",
						200 } };
	}

	@DataProvider(name = "addProduct")
	public static Object[][] addProduct() {

		return new Object[][] {

				// product title, product description, wallet, productId, UserId,
				// productTemplate, CatId, CatTitle, Expected Status Code
				{ "producttitle", "productdescription", "5cfa540bc7779014978de2bf", "5cd45444e119f26c39a55eea",
						"5cfa5407c7779014978de2be", "5cd454d7e119f26c39a55eeb", "5c8aa18b0605dc668d0f1003",
						"Smart Devices", 200 }

		};
	}

}