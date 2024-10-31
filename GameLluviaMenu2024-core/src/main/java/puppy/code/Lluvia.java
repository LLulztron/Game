/*package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Lluvia {
    private Array<Rectangle> rainDropsPos;
    private Array<Integer> rainDropsType;
    private long lastDropTime;
    private Texture gotaBuena;
    private Texture gotaMala;
    private Texture gotaBuff; // Nueva textura para la gota buff
    private Sound dropSound;
    private Music rainMusic;

    public Lluvia(Texture gotaBuena, Texture gotaMala, Texture gotaBuff, Sound ss, Music mm) { // Añadir parámetro para la gota buff
        rainMusic = mm;
        dropSound = ss;
        this.gotaBuena = gotaBuena;
        this.gotaMala = gotaMala;
        this.gotaBuff = gotaBuff; // Inicializar la textura de la gota buff
    }

    public void crear() {
        rainDropsPos = new Array<Rectangle>();
        rainDropsType = new Array<Integer>();
        crearGotaDeLluvia();
        rainMusic.setLooping(true);
        rainMusic.play();
    }

    private void crearGotaDeLluvia() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800 - 64);
        raindrop.y = 480;
        raindrop.width = 64;
        raindrop.height = 64;
        rainDropsPos.add(raindrop);

        // Determinar el tipo de gota
        int tipoGota = MathUtils.random(1, 20);
        if (tipoGota == 1) { // 10% de probabilidad para gota buff
            rainDropsType.add(3); // Gota buff
        } else if (tipoGota <= 5) { // 40% de probabilidad para gota dañina
            rainDropsType.add(1); // Gota dañina
        } else { // 50% de probabilidad para gota buena
            rainDropsType.add(2); // Gota buena
        }
        lastDropTime = TimeUtils.nanoTime();
    }

    public boolean actualizarMovimiento(Tarro tarro) {
        // Generar gotas de lluvia
        if (TimeUtils.nanoTime() - lastDropTime > 100000000) crearGotaDeLluvia();

        // Revisar si las gotas cayeron al suelo o chocaron con el tarro
        for (int i = 0; i < rainDropsPos.size; i++) {
            Rectangle raindrop = rainDropsPos.get(i);
            raindrop.y -= 300 * Gdx.graphics.getDeltaTime();
            // Caer al suelo y eliminar
            if (raindrop.y + 64 < 0) {
                rainDropsPos.removeIndex(i);
                rainDropsType.removeIndex(i);
            }
            if (raindrop.overlaps(tarro.getArea())) { // La gota choca con el tarro
                if (rainDropsType.get(i) == 1) { // Gota dañina
                    tarro.dañar();
                    if (tarro.getVidas() <= 0)
                        return false; // Si se queda sin vidas retorna falso / game over
                    rainDropsPos.removeIndex(i);
                    rainDropsType.removeIndex(i);
                } else if (rainDropsType.get(i) == 2) { // Gota buena
                    tarro.sumarPuntos(10);
                    dropSound.play();
                    rainDropsPos.removeIndex(i);
                    rainDropsType.removeIndex(i);
                } else { // Gota buff
                    tarro.aumentarVida(); // Método que deberías implementar en la clase Tarro
                    rainDropsPos.removeIndex(i);
                    rainDropsType.removeIndex(i);
                }
            }
        }
        return true;
    }

    public void actualizarDibujoLluvia(SpriteBatch batch) {
        for (int i = 0; i < rainDropsPos.size; i++) {
            Rectangle raindrop = rainDropsPos.get(i);
            if (rainDropsType.get(i) == 1) // Gota dañina
                batch.draw(gotaMala, raindrop.x, raindrop.y);
            else if (rainDropsType.get(i) == 2) // Gota buena
                batch.draw(gotaBuena, raindrop.x, raindrop.y);
            else // Gota buff
                batch.draw(gotaBuff, raindrop.x, raindrop.y); // Dibujar la gota buff
        }
    }

    public void destruir() {
        dropSound.dispose();
        rainMusic.dispose();
    }

    public void pausar() {
        rainMusic.stop();
    }

    public void continuar() {
        rainMusic.play();
    }
}*/ 

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
}
