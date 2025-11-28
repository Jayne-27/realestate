import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RealEstateClassExistsTest {

    @Test
    public void realEstateClassIsPresent() {
        try {
            Class<?> clazz = Class.forName("RealEstate");
            assertNotNull(clazz, "RealEstate class should be present on the classpath");
        } catch (ClassNotFoundException e) {
            fail("RealEstate class not found. Ensure src is compiled and the class is in the default package or update the test's class name.");
        }
    }
}