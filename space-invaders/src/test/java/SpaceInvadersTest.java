import org.junit.Test;
import ru.itis.game.SpaceInvaders;

import static org.junit.Assert.*;

public class SpaceInvadersTest {

    @Test
    public void testDistanceOn7384() {
        int actual = SpaceInvaders.distance(7,8,3,5);
        int expected = 5;
        assertEquals(expected, actual);
    }
}
