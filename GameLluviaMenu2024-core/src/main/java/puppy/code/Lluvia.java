package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Lluvia extends ElementoJuego implements Accionable {
    private Array<Rectangle> rainDropsPos;
    private Array<Integer> rainDropsType;
    private long lastDropTime;
    private Texture gotaBuena;
    private Texture gotaMala;
    private Texture gotaBuff;
    private Sound dropSound;
    private Sound buffDropSound;   // Sonido para la gota buff (healthDrop)
    private Music rainMusic;
    private float velocidad = 300;
    private Tarro tarro;

    public Lluvia(Texture gotaBuena, Texture gotaMala, Texture gotaBuff, Sound dropSound, Sound buffDropSound, Music rainMusic, Tarro tarro) {
        super(0, 480);
        this.gotaBuena = gotaBuena;
        this.gotaMala = gotaMala;
        this.gotaBuff = gotaBuff;
        this.dropSound = dropSound;
        this.buffDropSound = buffDropSound;
        this.rainMusic = rainMusic;
        this.tarro = tarro;
        rainDropsPos = new Array<>();
        rainDropsType = new Array<>();
        crearGotaDeLluvia();
        rainMusic.setLooping(true);
        rainMusic.play();
    }

    private void crearGotaDeLluvia() {
        Rectangle raindrop = new Rectangle();
        boolean posicionLibre;
        int intentos = 0;

        do {
            raindrop.x = MathUtils.random(0, 500 - 64);
            raindrop.y = posY;
            raindrop.width = 64;
            raindrop.height = 64;

            posicionLibre = true;
            for (Rectangle existente : rainDropsPos) {
                if (raindrop.overlaps(existente)) {
                    posicionLibre = false;
                    break;
                }
            }
            intentos++;
        } while (!posicionLibre && intentos < 10);

        if (posicionLibre) {
            rainDropsPos.add(raindrop);

            int probabilidadMala = 5 + (tarro.getPuntos() / 10);
            probabilidadMala = MathUtils.clamp(probabilidadMala, 5, 80);

            int tipoGota;
            int randomValue = MathUtils.random(1, 100);
            if (randomValue <= probabilidadMala) {
                tipoGota = 1; // Gota mala
            } else if (randomValue <= probabilidadMala + 5) {
                tipoGota = 3; // Gota buff
            } else {
                tipoGota = 2; // Gota buena
            }
            rainDropsType.add(tipoGota);
            lastDropTime = TimeUtils.nanoTime();
        }
    }

    @Override
    public void actualizar() {
        float velocidadIncremento = tarro.getPuntos() / 1000.0f * 500;
        velocidad = 300 + velocidadIncremento;

        if (TimeUtils.nanoTime() - lastDropTime > 450000000) {
            int gotasPorCrear = MathUtils.random(1, 3);
            for (int i = 0; i < gotasPorCrear; i++) {
                crearGotaDeLluvia();
            }
            lastDropTime = TimeUtils.nanoTime();
        }

        for (Rectangle raindrop : rainDropsPos) {
            raindrop.y -= velocidad * Gdx.graphics.getDeltaTime();
        }
    }

    @Override
    public void dibujar(SpriteBatch batch) {
        for (int i = 0; i < rainDropsPos.size; i++) {
            Rectangle raindrop = rainDropsPos.get(i);
            Texture gota = rainDropsType.get(i) == 1 ? gotaMala : (rainDropsType.get(i) == 2 ? gotaBuena : gotaBuff);
            batch.draw(gota, raindrop.x, raindrop.y);
        }
    }

    public boolean verificarColision(Tarro tarro) {
        for (int i = 0; i < rainDropsPos.size; i++) {
            Rectangle raindrop = rainDropsPos.get(i);
            if (raindrop.overlaps(tarro.getArea())) {
                if (rainDropsType.get(i) == 1) {  // Gota mala
                    tarro.dañar();
                    if (tarro.getVidas() <= 0) return false;
                } else if (rainDropsType.get(i) == 2) {  // Gota buena
                    tarro.sumarPuntos(10);
                    dropSound.play();  // Reproduce el sonido de gota buena
                } else {  // Gota buff (healthDrop)
                    tarro.aumentarVida();
                    buffDropSound.play();  // Reproduce el sonido de gota buff una vez
                }
                // Elimina la gota después de la colisión para evitar múltiples reproducciones
                rainDropsPos.removeIndex(i);
                rainDropsType.removeIndex(i);
                i--;  // Ajusta el índice después de eliminar un elemento
            }
        }
        return true;
    }

    @Override
    public void destruir() {
        gotaBuena.dispose();
        gotaMala.dispose();
        gotaBuff.dispose();
        dropSound.dispose();
        buffDropSound.dispose(); // Liberar el sonido de gota buff
        rainMusic.dispose();
    }

    public void pausar() {
        if (rainMusic.isPlaying()) {
            rainMusic.pause();
        }
    }

    public void continuar() {
        if (!rainMusic.isPlaying()) {
            rainMusic.play();
        }
    }

    @Override
    public void actualizarMovimiento() { }
}