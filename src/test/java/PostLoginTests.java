import io.restassured.response.Response;
import models.User;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;
import static requests.LoginEndpoints.postLoginRequest;
import static requests.UserEndpoints.*;

public class PostLoginTests extends TestBase{

    private User validUser;
    private User invalidUser;

    @BeforeClass
    public void generateTestData(){
        validUser = new User("Mari", "mari@email.com", "123abc", "true");
        postUserRequest(SPEC, validUser);
        invalidUser = new User("Mari", "mari@email.com", "asdas", "true");
    }

    @Test
    public void shouldReturnSuccessMessageAuthTokenAndStatus200(){
        Response loginSuccessResponse = postLoginRequest(SPEC, validUser);
        loginSuccessResponse.
            then().
                assertThat().
                statusCode(200).
                body("message", equalTo(Constants.MESSAGE_SUCCESS_LOGIN)).
                body("authorization", notNullValue());

    }

    @Test
    public void shouldReturnFailureMessageAndStatus401(){

        Response loginFailureResponse = postLoginRequest(SPEC, invalidUser);
        loginFailureResponse.
            then().
                assertThat().
                statusCode(401).
                body("message", equalTo(Constants.MESSAGE_FAILED_LOGIN)).
                body("authorization", nullValue());
    }

    @AfterClass
    public void removeTestData(){
        deleteUserRequest(SPEC, validUser);
    }
}
