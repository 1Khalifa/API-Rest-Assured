import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

public class FoodicsTest {

    private static final String BASE_URL = "https://pay2.foodics.dev/cp_internal";
    private static final String TOKEN = "Lyz22cfYKMetFhKQybx5HAmVimF1i0xO";

    @Test
    public void testLoginSuccess() {
        Response response = RestAssured.given()
                .contentType("application/json")
                .body("{\"email\": \"merchant@foodics.com\", \"password\": \"123456\"}")
                .post(BASE_URL + "/login");

        // Validate response status code
        Assert.assertEquals(200, response.getStatusCode());
        
    }
    
    @Test
    public void testLoginFailure() {
        // Prepare request body with incorrect credentials
        String requestBody = "{\"email\": \"merchant@foodics.com\", \"password\": \"654321\"}";

        // Send a POST request with incorrect credentials
        Response response = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .post(BASE_URL + "/login");

        // Validate response status code
        Assert.assertEquals(401, response.getStatusCode());

    }

    @Test
    public void testAuthorizationSuccess() {
        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + TOKEN)
                .get(BASE_URL + "/whoami");

        // Validate response status code
        Assert.assertEquals(200, response.getStatusCode());
        
    }

    @Test
    public void testAuthorizationFailure() {
        Response response = RestAssured.given()
                .header("Authorization", "Bearer InvalidToken")
                .get(BASE_URL + "/whoami");

        // Validate response status code
        Assert.assertNotEquals(200, response.getStatusCode());
        
    }
}