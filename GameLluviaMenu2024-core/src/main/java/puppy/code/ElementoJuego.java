package puppy.code;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class ElementoJuego implements Accionable {
    protected int posX, posY;

    public ElementoJuego(int x, int y) {
        this.posX = x;
        this.posY = y;
    }

    // Método Template
    public final void actualizarYDibujar(SpriteBatch batch) {
        actualizarMovimiento();
        dibujar(batch);
    }

    public abstract void actualizarMovimiento();
    public abstract void dibujar(SpriteBatch batch);
    public abstract void destruir();

    @Override
    public void continuar() {
        // Implementación genérica, si aplica
    }

    @Override
    public void pausar() {
        // Implementación genérica, si aplica
    }
}
