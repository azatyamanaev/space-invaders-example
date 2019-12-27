import org.junit.Test;
import ru.itis.game.SpaceInvaders;
import ru.itis.game.Spaceship;

import static org.junit.Assert.*;

public class SpaceshipTest {

    @Test
    public void testCollideTrue() {
        Spaceship spaceship = new Spaceship(50, 50, SpaceInvaders.PLAYER_SIZE, SpaceInvaders.PLAYER_IMG);
        Spaceship other = new Spaceship(25, 25, SpaceInvaders.PLAYER_SIZE, SpaceInvaders.enemiesImg[0]);
        assertTrue(spaceship.collide(other));
    }

    @Test
    public void testCollideFalse() {
        Spaceship spaceship = new Spaceship(100, 100, SpaceInvaders.PLAYER_SIZE, SpaceInvaders.PLAYER_IMG);
        Spaceship other = new Spaceship(250, 250, SpaceInvaders.PLAYER_SIZE, SpaceInvaders.enemiesImg[0]);
        assertFalse(spaceship.collide(other));
    }
}
