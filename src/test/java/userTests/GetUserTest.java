package userTests;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.testng.TestNGCitrusSupport;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;
import pojo.Data;
import pojo.Support;
import pojo.User;

import static com.consol.citrus.actions.EchoAction.Builder.echo;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class GetUserTest extends TestNGCitrusSupport {
    private TestContext context;
    @Test(description = "get information about user")
    @CitrusTest
    public void getTestActions() {
        this.context = citrus.getCitrusContext().createTestContext();

        $(echo("We have userId = " + context.getVariable("userId")));
        $(echo("Property \"userId\" = " + "${userId}"));

        variable("now", "citrus:currentDate()");
        $(echo("Today is: ${now}"));

        $(http()
                .client("restClientReqres")
                .send()
                .get("users/" + context.getVariable("userId"))
        );

        $(http()
                .client("restClientReqres")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .type(MessageType.JSON)
                .body(new ClassPathResource("json/user2.json"))
        );
    }
}
