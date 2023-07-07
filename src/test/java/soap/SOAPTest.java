package soap;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.testng.TestNGCitrusSupport;
import org.testng.annotations.Test;

public class SOAPTest extends TestNGCitrusSupport {
    private TestContext context;

    @Test(description = "SOAP")
    @CitrusTest
    public void SOAPTest() {
        this.context = citrus.getCitrusContext().createTestContext();


    }
}
