package requests;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.User;
import org.json.simple.JSONObject;

import static io.restassured.RestAssured.given;

public class UserEndpoint extends RequestBase{

    public static Response authenticateUserRequest(RequestSpecification spec, User user){
        Response loginResponse =
                given().
                        spec(spec).
                        header("Content-Type","application/json").
                        and().
                        body(user.getUserCredentialsAsJson()).
                        when().post("login");

        user.setUserAuthToken(getValueFromResponse(loginResponse, "authorization"));
        return loginResponse;
    }

    public static Response getUserRequest(RequestSpecification spec, String query){
        Response getUserResponse =
                given().
                        spec(spec).
                        when().
                        get("usuarios"+query);
        return getUserResponse;
    }

    public static Response registerUserRequest(RequestSpecification spec, User user){
        JSONObject userJsonRepresentation = new JSONObject();
        userJsonRepresentation.put("nome", user.name);
        userJsonRepresentation.put("email",user.email);
        userJsonRepresentation.put("password", user.password);
        userJsonRepresentation.put("administrador",user.isAdmin);

        Response registerUserResponse =
                given().
                        spec(spec).
                        header("Content-Type","application/json").
                        and().
                        body(userJsonRepresentation.toJSONString()).
                        when().
                        post("usuarios");

        user.setUserId(getValueFromResponse(registerUserResponse, "_id"));
        return registerUserResponse;
    }

    public static Response deleteUserRequest(RequestSpecification spec, User user){
        Response deleteUserResponse =
                given().
                        spec(spec).
                        header("Content-Type","application/json").
                        when().
                        delete("usuarios/"+user.userID);
        return deleteUserResponse;
    }
}
