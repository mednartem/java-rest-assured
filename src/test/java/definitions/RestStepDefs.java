package definitions;

import io.cucumber.java.en.Given;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import support.RandomData;

import java.util.Arrays;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class RestStepDefs {

    public void setup() {
        RestAssured.baseURI = "https://skryabin.com";
        RestAssured.basePath = "/recruit/api/v1";
    }

    public RequestSpecification getRestAssured() {
        setup();
        return given()
                .log().all();
    }

    public RequestSpecification getRequestSpecification() {
        setup();
        return new RequestSpecBuilder()
                .build();
    }

    public ResponseSpecification getResponseSpecification() {
        return new ResponseSpecBuilder()
                .expectStatusCode(Matchers.is(Matchers.in(
                        Arrays.asList(HttpStatus.SC_OK, HttpStatus.SC_CREATED))
                ))
                .build();
    }

//    @Given("I get candidates via api")
//    public void iGetCandidatesViaApi() {
//        given()
//                .spec(getRequestSpecification())
//                .when()
//                .get("/candidates")
//                .then().log().all()
//                .spec(getResponseSpecification());
//    }

    @Given("I get candidates via api")
    public void iGetCandidates() {
        given()
                .spec(getRequestSpecification())
                .when()
                .get("/candidates")
                .then().log().all()
                .spec(getResponseSpecification())
                .body("findAll {it.id < 3}.firstName", Matchers.hasItems(1, 2));
    }

    @Given("I get one candidate with id: {int}")
    public void iGetCandidateWithId(int id) {
        RestAssured.get("https://skryabin.com/recruit/api/v1/candidates/" + id)
                .then()
                .spec(getResponseSpecification());
//        RestAssured
//                .given()
//                .baseUri("https://skryabin.com")
//                .basePath("/recruit/api/v1")
//                .log().all()
//                .when()
//                .get("/candidates/" + id)
//                .then().log().all()
//                .statusCode(HttpStatus.SC_OK);
    }

    @Given("I create candidate")
    public void iCreateCandidate() {
        String name = "John";

        given().log().all()
                .spec(getRequestSpecification())
                .contentType(ContentType.JSON)
                .body(new HashMap<String, String>() {{
                    put("firstName", name);
                    put("lastName", "Does12");
                    put("email", RandomData.getRandomEmail());
                    put("password", "welcome");
                }})
                .when()
                .post("candidates")
                .then().log().all()
                .spec(getResponseSpecification())
                .body("firstName", Matchers.equalTo(name));
    }
}
