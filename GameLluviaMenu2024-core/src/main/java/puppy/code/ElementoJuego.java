package puppy.code;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class ElementoJuego {
    protected int posX, posY; // Ejemplo de posición común

    public ElementoJuego(int x, int y) {
        this.posX = x;
        this.posY = y;
    }

    // Método para actualizar movimiento de cada elemento
    public abstract void actualizarMovimiento();

    // Método para dibujar en pantalla
    public abstract void dibujar(SpriteBatch batch);

    // Método para liberar recursos
    public abstract void destruir();
}
