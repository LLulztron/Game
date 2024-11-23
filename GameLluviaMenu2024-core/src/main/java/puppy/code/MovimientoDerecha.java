package puppy.code;

import com.badlogic.gdx.math.Rectangle;

public class MovimientoDerecha implements Movimiento {
	 @Override
	    public void mover(Rectangle bucket, float velocidad, float deltaTime) {
	        bucket.x += velocidad * deltaTime; // Mover hacia la derecha
	        // Evitar que salga del límite derecho
	        bucket.x = Math.min(bucket.x, Constants.SCREEN_WIDTH - bucket.width);
	    }
}
