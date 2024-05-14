package WizardTD;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PairTest {

    @Test
    public void testEquals() {
        // Test pair functionality works when identical pairs
        Pair pair1 = new Pair(1, 2);
        Pair pair2 = new Pair(1, 2);
        Pair pair3 = new Pair(2, 2);
        Pair pair4 = new Pair(1, 3);

        assertTrue(pair1.equals(pair2));
        assertFalse(pair1.equals(pair3));
        assertFalse(pair1.equals(pair4));
        assertFalse(pair1.equals(null));
        assertFalse(pair1.equals(new Object()));
    }

    @Test
    public void testHashCode() {
        // Test hashcode of different pairs
        Pair pair1 = new Pair(1, 2);
        Pair pair2 = new Pair(1, 2);
        Pair pair3 = new Pair(2, 2);

        assertEquals(pair1.hashCode(), pair2.hashCode());
        assertNotEquals(pair1.hashCode(), pair3.hashCode());
    }

    @Test
    public void testSelfEquality() {
        // Test pair is equal to itself
        Pair pair = new Pair(1, 2);
        assertTrue(pair.equals(pair));
    }
}
