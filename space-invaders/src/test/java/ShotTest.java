import org.junit.Test;
import ru.itis.game.Shot;
import ru.itis.game.SpaceInvaders;
import ru.itis.game.Spaceship;

import static org.junit.Assert.*;

public class ShotTest {

    @Test
    public void testCollideTrue(){
        Shot shot = new Shot(80, 80);
        Spaceship spaceship = new Spaceship(50, 50, SpaceInvaders.PLAYER_SIZE, SpaceInvaders.enemiesImg[0]);
        boolean actual = shot.collide(spaceship);
        assertTrue(actual);
    }

    @Test
    public void testCollideFalse(){
        Shot shot = new Shot(10, 10);
        Spaceship spaceship = new Spaceship(100, 100, SpaceInvaders.PLAYER_SIZE, SpaceInvaders.enemiesImg[0]);
        boolean actual = shot.collide(spaceship);
        assertFalse(actual);
    }
}
