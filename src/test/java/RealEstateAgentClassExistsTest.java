import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RealEstateAgentClassExistsTest {

    @Test
    public void realEstateAgentClassIsPresent() {
        try {
            Class<?> clazz = Class.forName("RealEstateAgent");
            assertNotNull(clazz, "RealEstateAgent class should be present on the classpath");
        } catch (ClassNotFoundException e) {
            fail("RealEstateAgent class not found. Ensure src is compiled and the class is in the default package or update the test's class name.");
        }
    }
}