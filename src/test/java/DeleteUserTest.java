import io.restassured.response.Response;
import models.User;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;
import static requests.UserEndpoint.*;

public class DeleteUserTest extends TestBase{

    private User validUser;

    @BeforeClass
    public void generateTestData(){
        validUser = new User("Tatu", "tatu@email.com", "123abc", "true");
        registerUserRequest(SPEC, validUser);
    }

    @Test
    public void shouldRemoveUserReturnSuccessMessageAndStatus200(){
        Response deleteUserResponse = deleteUserRequest(SPEC, validUser);
        deleteUserResponse.
        then().
                assertThat().
                statusCode(200).
                body("message", equalTo(Constants.MESSAGE_SUCCESS_DELETION));
    }
}
