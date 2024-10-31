package puppy.code;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Accionable {
    void actualizar();
    void dibujar(SpriteBatch batch);
	void continuar();
	void pausar();
	void destruir();
}
