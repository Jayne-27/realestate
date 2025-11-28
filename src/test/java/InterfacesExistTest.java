import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InterfacesExistTest {

    @Test
    public void propertyInterfaceExists() {
        try {
            Class<?> clazz = Class.forName("PropertyInterface");
            assertTrue(clazz.isInterface(), "PropertyInterface should be an interface");
        } catch (ClassNotFoundException e) {
            fail("PropertyInterface not found. Check the interface name or package.");
        }
    }

    @Test
    public void panelInterfaceExists() {
        try {
            Class<?> clazz = Class.forName("PanelInterface");
            assertTrue(clazz.isInterface(), "PanelInterface should be an interface");
        } catch (ClassNotFoundException e) {
            fail("PanelInterface not found. Check the interface name or package.");
        }
    }
}