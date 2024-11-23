package puppy.code;

import com.badlogic.gdx.math.Rectangle;
//Esta es para realizar el patron de diseño STRATEGY

public interface Movimiento {
    /**
     * Define el comportamiento del movimiento del objeto.
     *
     * @param bucket     El rectángulo que representa la posición del objeto.
     * @param velocidad  La velocidad del movimiento.
     * @param deltaTime  El tiempo transcurrido desde el último cuadro.
     */
    void mover(Rectangle bucket, float velocidad, float deltaTime);
}
