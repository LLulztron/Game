package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Tarro extends ElementoJuego {
    private final Rectangle bucket;
    private final Texture bucketImage;
    private final Sound sonidoHerido;
    private final Sound sonidoCurar;
    private final int velocidadNormal = 400;  // Velocidad normal
    private int velocidadActual = velocidadNormal;  // Velocidad actual (puede ser modificada por efectos)
    private int vidas = 3;
    private int puntos = 0;

    private Movimiento movimiento; // Estrategia de movimiento

    // Variables para la ralentización
    private float tiempoRalentizado = 0;  // Tiempo durante el cual el tarro está ralentizado

    public Tarro(Texture bucketImage, Sound sonidoHerido, Sound sonidoCurar) {
        super(Constants.SCREEN_WIDTH / 2 - 32, 20);
        this.bucketImage = bucketImage;
        this.sonidoHerido = sonidoHerido;
        this.sonidoCurar = sonidoCurar;
        this.bucket = new Rectangle(posX, posY, 64, 64);
        this.movimiento = null; // Por defecto, no hay movimiento
    }

    /**
     * Establece la estrategia de movimiento para el tarro.
     * 
     * @param movimiento La estrategia de movimiento.
     */
    public void setMovimiento(Movimiento movimiento) {
        this.movimiento = movimiento;
    }

    public int getVidas() {
        return vidas;
    }

    public int getPuntos() {
        return puntos;
    }

    @Override
    public void actualizarMovimiento() {
        // Si el tarro está ralentizado, actualiza la velocidad
        if (movimiento != null) {
            movimiento.mover(bucket, velocidadActual, Gdx.graphics.getDeltaTime());
        }
    }

    @Override
    public void dibujar(SpriteBatch batch) {
        batch.draw(bucketImage, bucket.x, bucket.y);
    }

    @Override
    public void destruir() {
        // Liberar recursos asociados al tarro si es necesario
    }

    public void sumarPuntos() {
        puntos += 10;
    }

    public void GoldenPoint() {
        puntos += 100;
    }

    public void sumarVida() {
        vidas++;
        sonidoCurar.play();
    }

    public void quitarVida() {
        vidas--;
        sonidoHerido.play();
    }

    public boolean colisionaConGota(Rectangle raindrop) {
        return bucket.overlaps(raindrop);
    }

    @Override
    public void actualizar() {
        // Si el efecto de ralentización ha terminado, restauramos la velocidad normal
        if (tiempoRalentizado > 0) {
            tiempoRalentizado -= Gdx.graphics.getDeltaTime();
            if (tiempoRalentizado <= 0) {
                velocidadActual = velocidadNormal;  // Restablecer la velocidad normal
            }
        }
    }

    // Método para aplicar el efecto de ralentización
    public void aplicarRalentizacion(float duracion) {
        tiempoRalentizado = duracion;  // Establece la duración del efecto de ralentización
        velocidadActual = (int) (velocidadNormal * 0.5f);  // Reduce la velocidad al 50%
    }
}
