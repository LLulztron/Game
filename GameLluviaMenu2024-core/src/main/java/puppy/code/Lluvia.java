/*
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

public class Lluvia extends ElementoJuego {
    private Array<Rectangle> rainDropsPos;
    private Array<Integer> rainDropsType;
    private long lastDropTime;
    private Texture gotaBuena;
    private Texture gotaMala;
    private Texture gotaBuff;
    private Sound dropSound;
    private Music rainMusic;

    public Lluvia(Texture gotaBuena, Texture gotaMala, Texture gotaBuff, Sound ss, Music mm) {
        super(0, 480); // Inicializa en la posición superior
        this.gotaBuena = gotaBuena;
        this.gotaMala = gotaMala;
        this.gotaBuff = gotaBuff;
        this.dropSound = ss;
        this.rainMusic = mm;
        rainDropsPos = new Array<>();
        rainDropsType = new Array<>();
        crearGotaDeLluvia();
        rainMusic.setLooping(true);
        rainMusic.play();
    }

    private void crearGotaDeLluvia() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800 - 64);
        raindrop.y = posY;
        raindrop.width = 64;
        raindrop.height = 64;
        rainDropsPos.add(raindrop);

        int tipoGota = MathUtils.random(1, 20);
        rainDropsType.add(tipoGota == 1 ? 3 : (tipoGota <= 5 ? 1 : 2));
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void actualizarMovimiento() {
        if (TimeUtils.nanoTime() - lastDropTime > 100000000)
            crearGotaDeLluvia();
        
        for (Rectangle raindrop : rainDropsPos) {
            raindrop.y -= 300 * Gdx.graphics.getDeltaTime();
        }
    }

    @Override
    public void dibujar(SpriteBatch batch) {
        for (int i = 0; i < rainDropsPos.size; i++) {
            Rectangle raindrop = rainDropsPos.get(i);
            batch.draw(rainDropsType.get(i) == 1 ? gotaMala : (rainDropsType.get(i) == 2 ? gotaBuena : gotaBuff), raindrop.x, raindrop.y);
        }
    }

    public boolean verificarColision(Tarro tarro) {
        for (int i = 0; i < rainDropsPos.size; i++) {
            Rectangle raindrop = rainDropsPos.get(i);
            if (raindrop.overlaps(tarro.getArea())) {
                if (rainDropsType.get(i) == 1) {
                    tarro.dañar();
                    if (tarro.getVidas() <= 0)
                        return false;
                } else if (rainDropsType.get(i) == 2) {
                    tarro.sumarPuntos(10);
                    dropSound.play();
                } else {
                    tarro.aumentarVida();
                }
                rainDropsPos.removeIndex(i);
                rainDropsType.removeIndex(i);
            }
        }
        return true;
    }

    @Override
    public void destruir() {
        dropSound.dispose();
        rainMusic.dispose();
    }

	public void continuar() {
		// TODO Auto-generated method stub
		
	}

	public void pausar() {
		// TODO Auto-generated method stub
		
	}

	public void crear() {
		// TODO Auto-generated method stub
		
	}
} */

// Archivo: Lluvia.java
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
    private Array<Rectangle> rainDropsPos;   // Posiciones de las gotas
    private Array<Integer> rainDropsType;    // Tipos de gotas (1 = mala, 2 = buena, 3 = buff)
    private long lastDropTime;               // Tiempo de la última gota creada
    private Texture gotaBuena;
    private Texture gotaMala;
    private Texture gotaBuff;
    private Sound dropSound;
    private Music rainMusic;
    private float velocidad = 300;           // Velocidad base de caída de las gotas
    private Tarro tarro;                     // Referencia al Tarro para acceder al puntaje

    public Lluvia(Texture gotaBuena, Texture gotaMala, Texture gotaBuff, Sound dropSound, Music rainMusic, Tarro tarro) {
        super(0, 480);
        this.gotaBuena = gotaBuena;
        this.gotaMala = gotaMala;
        this.gotaBuff = gotaBuff;
        this.dropSound = dropSound;
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
        raindrop.x = MathUtils.random(0, 800 - 64);
        raindrop.y = posY;
        raindrop.width = 64;
        raindrop.height = 64;
        rainDropsPos.add(raindrop);

        // Calcular la probabilidad de que la gota sea mala en función del puntaje del jugador
        int probabilidadMala = 5 + (tarro.getPuntos() / 100);  // Incrementa 1% de probabilidad de gota mala por cada 100 puntos
        probabilidadMala = MathUtils.clamp(probabilidadMala, 5, 80); // Limita la probabilidad entre 5% y 80%

        int tipoGota;
        int randomValue = MathUtils.random(1, 100);
        if (randomValue <= probabilidadMala) {
            tipoGota = 1; // Gota mala
        } else if (randomValue <= probabilidadMala + 10) {
            tipoGota = 3; // Gota buff (un 10% adicional para gotas buff)
        } else {
            tipoGota = 2; // Gota buena
        }
        rainDropsType.add(tipoGota);
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void actualizar() {
        // Incrementar la velocidad de caída de las gotas según el puntaje
        float velocidadIncremento = tarro.getPuntos() / 1000.0f * 500; // Incremento proporcional a los puntos
        velocidad = 300 + velocidadIncremento; // Velocidad base más incremento

        // Crear gotas de lluvia a intervalos regulares
        if (TimeUtils.nanoTime() - lastDropTime > 500000000) { // Cada 0.5 segundos
            int gotasPorCrear = MathUtils.random(1, 3);
            for (int i = 0; i < gotasPorCrear; i++) {
                crearGotaDeLluvia();
            }
            lastDropTime = TimeUtils.nanoTime();
        }

        // Actualizar posición de cada gota con la velocidad ajustada
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
                    if (tarro.getVidas() <= 0) return false;  // Fin del juego si se acaban las vidas
                } else if (rainDropsType.get(i) == 2) {  // Gota buena
                    tarro.sumarPuntos(10);
                    dropSound.play();
                } else {  // Gota buff (incrementa la vida)
                    tarro.aumentarVida();
                }
                rainDropsPos.removeIndex(i);
                rainDropsType.removeIndex(i);
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
	public void actualizarMovimiento() {
		// TODO Auto-generated method stub
		
	}
}
