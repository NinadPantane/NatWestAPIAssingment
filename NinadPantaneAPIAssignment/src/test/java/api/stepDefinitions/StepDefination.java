package api.stepDefinitions;

import static io.restassured.RestAssured.given;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import api.endpoints.Routes;
import api.payload.Payload;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class StepDefination {

	Payload payload = new Payload();

	RequestSpecification request = RestAssured.given();

	private Response response;
	private static String jsonString;
	private static String messageString;

	@Before
	public void beforeScenario() {
		ExtentCucumberAdapter.addTestStepLog("-----------------Start of Scenario-----------------");
	}

	@After
	public void afterScenario() {
		ExtentCucumberAdapter.addTestStepLog("-----------------End of Scenario-----------------");
	}

	@Given("I have the header {string} with value {string}")
	public void i_have_the_header_with_value(String header, String value) {
		request.header(header, value);
	}

	@Given("a {string} item is created")
	public void a_item_is_created(String name) {
		payload.setName(name);
	}

	@Given("is a {string} CPU model")
	public void is_a_CPU_model(String genration) {
		payload.setGeneration(genration);
	}

	@Given("has a price of {string}")
	public void has_a_price_of(String price) {
		payload.setPrice(price);
	}

	@When("the request to add the item is made")
	public void the_request_to_add_the_item_is_made() {

		String reqJsonBoday = "{\r\n" + "    \"name\": \"" + payload.getName() + "\",\r\n" + "    \"data\": {\r\n"
				+ "        \"Generation\": \"" + payload.getGeneration() + "\",\r\n" + "        \"Price\": \""
				+ payload.getPrice() + "\"\r\n" + "    }\r\n" + "}";

		response = given().contentType(ContentType.JSON).accept(ContentType.JSON).body(reqJsonBoday).when()
				.post(Routes.add_object);

		Assert.assertEquals(200, response.getStatusCode());
	}

	@Then("a {int} response code is returned")
	public void a_response_code_is_returned(int expectedResposeCode) {

		Assert.assertEquals((int) expectedResposeCode, response.getStatusCode());

	}

	@Then("a {string} is created")
	public void a_is_created(String name) {
		payload.setName(name);
		String jsonString = response.asString();
		String nameFromApiResponse = JsonPath.from(jsonString).get("name");
		Assert.assertEquals(nameFromApiResponse, payload.getName());
	}

	@Given("{string} is the product id for an item")
	public void is_the_product_id_for_an_item(String productId) {
		payload.setId(productId);
	}

	@When("the request is made to fecth an item")
	public void the_request_is_made_to_fecth_an_item() {

		response = given().pathParam("id", payload.getId()).when().get(Routes.get_object);
	}

	@Then("return the item deatils")
	public void return_the_item_deatils() {

		jsonString = response.asString();
		ExtentCucumberAdapter.addTestStepLog(" <br> " + "Product Returned from API " + " <br> ID: "
				+ JsonPath.from(jsonString).get("id") + " <br> Name: " + JsonPath.from(jsonString).get("name")
				+ " <br> Generation: " + JsonPath.from(jsonString).get("data.Generation") + " <br> Price: "
				+ JsonPath.from(jsonString).get("data.Price") + " <br> Capacity: "
				+ JsonPath.from(jsonString).get("data.Capacity"));
	}

	@When("request is made to fecth the list of muliple item")
	public void request_is_made_to_fecth_the_list_of_muliple_item() {

		response = request.get(Routes.list_object);
	}

	@Then("return all Device Names from the list")
	public void return_all_Device_Names_from_the_list() {

		jsonString = response.asString();
		List<String> productName = JsonPath.from(jsonString).get("name");

		ExtentCucumberAdapter.addTestStepLog(" <br>Device Names returned from API ");
		for (int i = 0; i < productName.size(); i++) {
			ExtentCucumberAdapter.addTestStepLog(productName.get(i));
		}
	}

	@Given("an item to be created with specification {string},{string} and {string}")
	public void an_item_to_be_created_with_specification_and(String name, String generation, String price) {

		payload.setName(name);
		payload.setGeneration(generation);
		payload.setPrice(price);
	}

	@When("the created item is deleted")
	public void the_created_item_is_deleted() {

		String jsonString = response.asString();
		payload.setId(JsonPath.from(jsonString).get("id"));

		response = given().pathParam("id", payload.getId()).when().delete(Routes.delete_object);
	}

	@Then("verify the DELETE API Success message")
	public void verify_the_delete_api_success_message() {

		jsonString = response.asString();
		messageString = JsonPath.from(jsonString).get("message");
		Assert.assertEquals("Object with id = " + payload.getId() + " has been deleted.", messageString);
		ExtentCucumberAdapter.addTestStepLog(messageString);
	}

	@When("the created item is deleted again")
	public void the_created_item_is_deleted_again() {
		response = given().pathParam("id", payload.getId()).when().delete(Routes.delete_object);
	}

	@Then("verify the DELETE API Error message")
	public void verify_the_delete_api_error_message() {
		jsonString = response.asString();
		messageString = JsonPath.from(jsonString).get("error");
		Assert.assertEquals("Object with id = " + payload.getId() + " doesn't exist.", messageString);
		ExtentCucumberAdapter.addTestStepLog(messageString);
	}

	@Then("verify the GET API Error message")
	public void verify_the_get_api_error_message() {
		jsonString = response.asString();
		messageString = JsonPath.from(jsonString).get("error");
		Assert.assertEquals("Oject with id=" + payload.getId() + " was not found.", messageString);
		ExtentCucumberAdapter.addTestStepLog(messageString);
	}

	@When("POST call is invoked without Request Body")
	public void post_call_invoked_without_request_body() {

		String reqJsonBoday = "";

		response = given().contentType(ContentType.JSON).accept(ContentType.JSON).body(reqJsonBoday).when().post(Routes.add_object);
		Assert.assertEquals(400, response.getStatusCode());
	}

	@Then("verify the POST API Error message")
	public void verify_the_post_api_error_message() {
		jsonString = response.asString();
		messageString = JsonPath.from(jsonString).get("error");
		Assert.assertTrue(messageString.contains("400 Bad Request."));
		ExtentCucumberAdapter.addTestStepLog(messageString);
	}

	@Then("return total number of Devices from the list which has associated Price")
	public void return_total_number_of_devices_from_the_list_which_has_associated_price() {
		jsonString = response.asString();
		List<String> productPrice = JsonPath.from(jsonString).get("data.price");
		int count = productPrice.size();
		productPrice.removeAll(Collections.singleton(null));
		ExtentCucumberAdapter.addTestStepLog(" <br>Out of Total Number: " + count + " of Devices found from the list "
				+ productPrice.size() + " has associated Price");
	}

	@Then("return total number of Devices from the list which dont have any associated Price")
	public void return_total_number_of_devices_from_the_list_which_dont_have_any_associated_price() {
		jsonString = response.asString();
		List<String> productPrice = JsonPath.from(jsonString).get("data.price");

		int count = 0;
		for (int i = 0; i < productPrice.size(); i++) {
			if (productPrice.get(i) == null) {
				count++;
			}
		}
		ExtentCucumberAdapter.addTestStepLog(" <br>Out of Total Number : " + productPrice.size()
				+ " of Devices found from the list " + count + " dont have any associated Price.");
	}
}
