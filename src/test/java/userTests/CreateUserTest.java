package userTests;

import behavoirs.CreateUserBehavior;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.testng.TestNGCitrusSupport;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;
import pojo.CreateUserResponse;

import static com.consol.citrus.actions.EchoAction.Builder.echo;
import static com.consol.citrus.dsl.JsonPathSupport.jsonPath;
import static com.consol.citrus.dsl.JsonSupport.json;
import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class CreateUserTest extends TestNGCitrusSupport {
    private TestContext context;
    String name = "Nick";
    String job = "Teacher";

    @Test(description = "create user test")
    @CitrusTest
    public void createUserTest() {
        this.context = citrus.getCitrusContext().createTestContext();

        run(applyBehavior(new CreateUserBehavior(name, job, context)));

        run(http()
                .client("restClientReqres")
                .receive()
                .response(HttpStatus.CREATED)
                .message()
                .type(MessageType.JSON)
                .body(new ObjectMappingPayloadBuilder(getResponseData(name, job), "objectMapper"))
                .validate(json()
                        .ignore("$.createdAt"))
                .validate(jsonPath()
                        .expression("$.name", name)
                        .expression("$.job", job))
                .extract(fromBody()
                        .expression("$.id", "currentId")
                        .expression("$.createdAt", "createdAt"))
        );
        $(echo("currentId = ${currentId} and createdAt = ${createdAt}"));
    }

    public CreateUserResponse getResponseData(String name, String job) {

        CreateUserResponse createUserResponse = new CreateUserResponse();
        createUserResponse.setName(name);
        createUserResponse.setJob(job);
        createUserResponse.setId("@isNumber()@");
        createUserResponse.setCreatedAt("unknown");

        return createUserResponse;
    }
}
