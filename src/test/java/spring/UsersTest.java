package spring;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class UsersTest {
    @BeforeClass
    public static void startWireMock() {
        configureFor(8080);
    }

    @Test
    public void getAllUsers() throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet request = new HttpGet("http://localhost:8080/user/all");
        HttpResponse httpResponse = httpClient.execute(request);
        String responseString = convertResponseToString(httpResponse);

        assertThat(httpResponse.getStatusLine().getStatusCode()).isEqualTo(200);
        assertThat(responseString).isNotEmpty();
        assertThat(responseString).isNotNull();
    }

    @Test
    public void getUserById() throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet request = new HttpGet("http://localhost:8080/user/5");
        HttpResponse httpResponse = httpClient.execute(request);
        String responseString = convertResponseToString(httpResponse);

        assertThat(httpResponse.getStatusLine().getStatusCode()).isEqualTo(200);
        assertThat(responseString).isNotEmpty();
        assertThat(responseString).isNotNull();
        assertThat(responseString).isEqualTo("{\"userId\":5,\"name\":\"Test user\",\"course\":\"QA\",\"email\":\"test@test.test\",\"age\":23,\"score\":78}");
    }

    private String convertResponseToString(HttpResponse response) throws IOException {
        InputStream responseStream = response.getEntity().getContent();
        Scanner scanner = new Scanner(responseStream, "UTF-8");
        String responseString = scanner.useDelimiter("\\Z").next();
        scanner.close();
        return responseString;
    }
}
