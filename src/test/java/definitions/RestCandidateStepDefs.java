package definitions;

import io.cucumber.java.en.Given;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import model.CandidateModel;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import support.RandomData;

import java.util.Map;

import static support.DataManager.getJuniorCandidateCredentialsFromFile;

public class RestCandidateStepDefs {
    public static RequestSpecification getRequestSpecification() {

        return new RequestSpecBuilder()
                .setBaseUri("https://skryabin.com/")
                .setBasePath("recruit/api/v1")
                .build();
    }

    @Given("I create and validate candidate")
    public void iCreateAndValidateCandidate() {
        Map<String, String> candidateData = getJuniorCandidateCredentialsFromFile();
//        Map<String, Object> postCandidate = postCandidate(candidateData);
//        String id = String.valueOf(postCandidate.get("id"));
//        Map<String, Object> map = getCandidate(Integer.parseInt(id));
//        Assertions.assertThat(map.get(Map<String, String> candidateData = getJuniorCandidateCredentialsFromFile();
        CandidateModel postCandidate = postCandidate(candidateData);
        CandidateModel getCandidate = getCandidate(postCandidate.getId());

        Assertions.assertThat(postCandidate.getId()).isEqualTo(getCandidate.getId());
        Assertions.assertThat(postCandidate.getFirstName()).isEqualTo(getCandidate.getFirstName());
    }

    private CandidateModel postCandidate(Map<String, String> body) {
        return RestAssured.given().log().all()
                .spec(getRequestSpecification())
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/candidates")
                .then().log().all()
                .statusCode(HttpStatus.SC_CREATED)
                .extract().as(CandidateModel.class);
    }

    private CandidateModel postCandidate(CandidateModel candidateModel) {
        return RestAssured.given().log().all()
                .spec(getRequestSpecification())
                .contentType(ContentType.JSON)
                .body(candidateModel)
                .when()
                .post("/candidates")
                .then().log().all()
                .statusCode(HttpStatus.SC_CREATED)
                .extract().as(CandidateModel.class);
    }

    private CandidateModel getCandidate(int id) {
        return RestAssured.given().log().all()
                .spec(getRequestSpecification())
                .when()
                .get("/candidates/" + id)
                .then().log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(CandidateModel.class);
    }
}
