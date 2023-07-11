package spring;

import helper.ConvertResponseToString;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.checkerframework.checker.units.qual.C;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static org.assertj.core.api.Assertions.assertThat;

public class CoursesTest {
    @BeforeClass
    public static void startWireMock() {
        configureFor(8080);
    }

    @Test
    public void getAllCourses() throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet request = new HttpGet("http://localhost:8080/course/all");
        HttpResponse httpResponse = httpClient.execute(request);
        String responseString = convertResponseToString(httpResponse);

        assertThat(httpResponse.getStatusLine().getStatusCode()).isEqualTo(200);
        assertThat(responseString).isNotEmpty();
        assertThat(responseString).isNotNull();
        assertThat(responseString).isEqualTo("[{\"name\":\"QA java\",\"price\":15000},{\"name\":\"java\",\"price\":12000}]");
    }

    public String convertResponseToString(HttpResponse response) throws IOException {
        InputStream responseStream = response.getEntity().getContent();
        Scanner scanner = new Scanner(responseStream, "UTF-8");
        String responseString = scanner.useDelimiter("\\Z").next();
        scanner.close();
        return responseString;
    }
}
