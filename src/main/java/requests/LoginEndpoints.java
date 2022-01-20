package requests;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.User;

import static io.restassured.RestAssured.given;

public class LoginEndpoints extends RequestBase {

    public static Response postLoginRequest(RequestSpecification spec, User user) {

        Response postLoginResponse =
                given().
                    spec(spec).
                    header("Content-Type", "application/json").
                    and().
                    body(user.getUserCredentialsAsJson()).
                when().
                        post("/login");
        user.setUserAuthToken(getValueFromResponse(postLoginResponse, "authorization"));
        return postLoginResponse;
    }
}
