package requests;

import com.google.gson.Gson;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.User;

import static io.restassured.RestAssured.given;

public class UserEndpoints extends RequestBase{

    public static Response getUserRequest(RequestSpecification spec){
        Response getUserResponse =
                given().
                        spec(spec).
                        when().
                        get("usuarios");
        return getUserResponse;
    }

    public static Response postUserRequest(RequestSpecification spec, User user){

        Gson gson = new Gson();
        String json = gson.toJson(user);

        Response registerUserResponse =
                given().
                        spec(spec).
                        header("Content-Type","application/json").
                        and().
                        body(json).
                        log().all().
                        when().
                        post("usuarios");

        user.setUserId(getValueFromResponse(registerUserResponse, "_id"));
        return registerUserResponse;
    }

    public static Response deleteUserRequest(RequestSpecification spec, User user){
        Response deleteUserResponse =
                given().
                        spec(spec).
                        pathParam("_id", user._id).
                        when().
                        delete("/usuarios/{_id}");

        return deleteUserResponse;
    }
}
