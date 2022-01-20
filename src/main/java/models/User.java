package models;
import org.json.simple.JSONObject;

public class User {

    public String nome;
    public String email;
    public String password;
    public String administrador;
    public String authToken;
    public String _id;

    public User(String nome, String email, String password, String administrador){
        this.nome = nome;
        this.email = email;
        this.password = password;
        this.administrador = administrador;
    }

    public void setUserAuthToken(String authToken){
        this.authToken= authToken;
    }

    public void setUserId(String userID){
        this._id = userID;
    }

    public String getUserCredentialsAsJson(){
        JSONObject userJsonRepresentation = new JSONObject();
        userJsonRepresentation.put("email",this.email);
        userJsonRepresentation.put("password",this.password);
        return userJsonRepresentation.toJSONString();
    }
}
