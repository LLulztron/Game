package puppy.code;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Accionable {
    /**
     * Interfaz para objetos del juego que pueden realizar acciones específicas.
     * Incluye métodos para actualizar, dibujar y gestionar estados (continuar, pausar, destruir).
     */
    void actualizar();
    void dibujar(SpriteBatch batch);
    void continuar();
    void pausar();
    void destruir();
}