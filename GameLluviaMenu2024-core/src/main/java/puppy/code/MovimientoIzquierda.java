package puppy.code;

import com.badlogic.gdx.math.Rectangle;

public class MovimientoIzquierda implements Movimiento {
	@Override
    public void mover(Rectangle bucket, float velocidad, float deltaTime) {
        bucket.x -= velocidad * deltaTime; // Mover hacia la izquierda
        // Evitar que salga del límite izquierdo
        bucket.x = Math.max(bucket.x, 0);
    }
}
