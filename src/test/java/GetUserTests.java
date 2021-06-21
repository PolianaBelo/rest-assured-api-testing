import io.restassured.response.Response;
import models.User;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
import static requests.UserEndpoint.*;

import org.testng.annotations.DataProvider;

public class GetUserTests extends TestBase{

    private User validUser1;
    private User validUser2;
    private User validUser3;
    private User invalidUser1;

    @BeforeClass
    public void generateTestData(){
        validUser1 = new User("Ana Silva", "ana@email.com", "123abc", "true");
        registerUserRequest(SPEC, validUser1);
        validUser2 = new User("Chico", "chico@email.com", "123abc", "true");
        registerUserRequest(SPEC, validUser2);
        validUser3 = new User("Maria", "mariasilva@email.com", "lalala123", "false");
        registerUserRequest(SPEC, validUser3);
        invalidUser1 = new User("Carlos", "carlos@email.com", "minhasenha", "0");
    }

    @AfterClass
    public void removeTestData(){
        deleteUserRequest(SPEC, validUser1);
        deleteUserRequest(SPEC, validUser2);
        deleteUserRequest(SPEC, validUser3);
    }

    @DataProvider(name = "usersData")
    public Object[][] createTestData() {
        return new Object[][] {
                {"?nome="+validUser1.name, 1},
                {"?password="+validUser2.password, 2},
                {"?email="+validUser3.email, 1},
                {"?nome="+invalidUser1.name+"&email="+invalidUser1.email, 0}
        };
    }

    @Test(dataProvider = "usersData")
    public void shouldReturnUsersAndStatus200(String query, int totalUsers){
        Response getUserResponse = getUserRequest(SPEC, query);
        getUserResponse.
            then().
                    assertThat().
                    statusCode(200).
                    body("quantidade", equalTo(totalUsers));
    }
}