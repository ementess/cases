package BackEnd;

import static io.restassured.RestAssured.*;
import com.cedarsoftware.util.io.JsonObject;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class ApiTestCases {

    String baseUrl="https://reqres.in";
    JsonObject requestBody = new JsonObject();

    @BeforeMethod
    public void testInitialization()
    {
        requestBody.clear();

    }

    @Test       // Requirement 1 #Done
    public void ResponseShouldNotContainAnyBook_IfThereIsNoAnyRecordOnDB(){
        //I assume that Response Body is received as "{}" when there is no any stored book on DB
        given().baseUri(baseUrl).when().get("/api/books/3891234").then().body(equalTo("{}"));
    }

    @Test       // Requirement 2.1 #Done
    public void ErrorMessageShouldBeReceived_IfTitleIsNull(){
        requestBody.put("author",null);
        requestBody.put("title","test title2_1");
        given().baseUri(baseUrl).contentType("application/json").body(requestBody).when().put("/api/books")
                .then().statusCode(400).body("error",equalTo("Field 'author' is required"));
    }

    @Test       // Requirement 2.2 #Done
    public void ErrorMessageShouldBeReceived_IfAuthorIsNull(){
        requestBody.put("author","test author2_2");
        requestBody.put("title",null);
        given().baseUri(baseUrl).contentType("application/json").body(requestBody).when().put("/api/books")
                .then().statusCode(400).body("error",equalTo("Field 'title' is required"));
    }

    @Test       // Requirement 3  #Done
    public void ErrorMessageShouldBeReceived_IfMandatoryFieldsAreEmpty(){
        requestBody.put("author","");
        requestBody.put("title","");
        given().baseUri(baseUrl).contentType("application/json").body(requestBody).when().put("/api/books").then().statusCode(400);
    }

    @Test       // Requirement 4  #Done
    public void ErrorMessageShouldBeReceived_IfRequestContainsIdValue(){
        requestBody.put("author","test author4");
        requestBody.put("title","test title4");
        requestBody.put("id",123456789);
        // I assume that 400 Bad Request is received if request contains ID in json
        given().baseUri(baseUrl).contentType("application/json").body(requestBody).when().put("/api/books")
                .then().statusCode(201)
                .body("error",equalTo("ID can't be set manually. Please remove it in your JSON!"));
    }


    @Test       // Requirement 5
    public void ANewBookShouldBeAbletoBeAdded_IfEnteredValuesAreValid(){
        requestBody.put("author","test author5");
        requestBody.put("title","test title5");
        // I assume that 400 Bad Request is received if request contains ID in json

        given().baseUri(baseUrl).contentType("application/json").body(requestBody).when().put("/api/books")
                .then().statusCode(201)
                .body("author",equalTo("test author"))
                .body("title",equalTo("test title"))
                .body("id",notNullValue());

    }

    @Test // Requirement6  #Done
    public void ErrorMessageShouldBeReceived_IfTitleAndAuthorValuesAreNotUnique()
    {
        requestBody.put("author","test author6");
        requestBody.put("title","test title6");

        given().baseUri(baseUrl).contentType("application/json").body(requestBody).when().put("/api/books")
                .then().statusCode(201)
                .body("author",equalTo("test author6"))
                .body("title",equalTo("test title6"));

        given().baseUri(baseUrl).contentType("application/json").body(requestBody).when().put("/api/books")
                .then().statusCode(409)
                .body("error",equalTo("Another book with similar title and author already exists."));

    }


}