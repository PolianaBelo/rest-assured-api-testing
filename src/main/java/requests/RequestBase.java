package requests;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class RequestBase {

    public static String getValueFromResponse(Response response, String value){
        JsonPath jsonPathEvaluator = response.jsonPath();
        return jsonPathEvaluator.get(value);
    }

}
