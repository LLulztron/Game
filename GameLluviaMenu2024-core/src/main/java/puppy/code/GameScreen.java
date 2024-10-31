/*package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {
    final GameLluviaMenu game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;
    private Tarro tarro;
    private Lluvia lluvia;

    private Texture backgroundImage; // Fondo de juego
    private boolean isPaused; // Estado de pausa

    public GameScreen(final GameLluviaMenu game) {
        this.game = game;
        this.batch = game.getBatch();
        this.font = game.getFont();
        
        backgroundImage = new Texture(Gdx.files.internal("Fondo.jpg")); // Ajusta el nombre del archivo de imagen

        tarro = new Tarro(new Texture(Gdx.files.internal("bucket.png")), Gdx.audio.newSound(Gdx.files.internal("hurt.ogg")));
        lluvia = new Lluvia(
            new Texture(Gdx.files.internal("drop.png")),
            new Texture(Gdx.files.internal("dropBad.png")),
            new Texture(Gdx.files.internal("healthDrp.png")),
            Gdx.audio.newSound(Gdx.files.internal("drop.wav")),
            Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"))
        );
        
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        
        // Inicialización de elementos de juego
        tarro.crear();
        lluvia.crear();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.1f, 1);
        
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        batch.draw(backgroundImage, 0, 0, camera.viewportWidth, camera.viewportHeight);

        font.draw(batch, "Gotas totales: " + tarro.getPuntos(), 5, 475);
        font.draw(batch, "Vidas : " + tarro.getVidas(), 670, 475);
        font.draw(batch, "HighScore : " + game.getHigherScore(), camera.viewportWidth / 2 - 50, 475);
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            isPaused = !isPaused;
            if (isPaused) {
                game.setScreen(new PausaScreen(game, this));
            }
        }

        if (!isPaused) {
            // Actualizar y dibujar elementos de juego usando métodos de ElementoJuego
            tarro.actualizarMovimiento();
            lluvia.actualizarMovimiento();

            if (!lluvia.verificarColision(tarro)) {
                if (game.getHigherScore() < tarro.getPuntos())
                    game.setHigherScore(tarro.getPuntos());  
                game.setScreen(new GameOverScreen(game));
                dispose();
            }
            
            tarro.dibujar(batch);
            lluvia.dibujar(batch);
        }
        
        batch.end();
    }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void show() {
        lluvia.continuar();
    }

    @Override
    public void hide() { }

    @Override
    public void pause() {
        lluvia.pausar();
        game.setScreen(new PausaScreen(game, this)); 
    }

    @Override
    public void resume() { }

    @Override
    public void dispose() {
        tarro.destruir();
        lluvia.destruir();
        backgroundImage.dispose();
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

	public void resumeRainSound() {
		// TODO Auto-generated method stub
		
	}
}*/


// Archivo: GameScreen.java
package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {
    final GameLluviaMenu game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;
    private List<Accionable> elementos;
    private Texture backgroundImage;
    private boolean isPaused;

    private Tarro tarro;      // Declarar Tarro a nivel de clase
    private Lluvia lluvia;    // Declarar Lluvia a nivel de clase

    public GameScreen(final GameLluviaMenu game) {
        this.game = game;
        this.batch = game.getBatch();
        this.font = game.getFont();

        backgroundImage = new Texture(Gdx.files.internal("Fondo.jpg"));

        // Inicializar elementos y asignar referencias
        tarro = new Tarro(new Texture(Gdx.files.internal("bucket.png")), 
                          Gdx.audio.newSound(Gdx.files.internal("hurt.ogg")));
        lluvia = new Lluvia(
            new Texture(Gdx.files.internal("drop.png")),
            new Texture(Gdx.files.internal("dropBad.png")),
            new Texture(Gdx.files.internal("healthDrp.png")),
            Gdx.audio.newSound(Gdx.files.internal("drop.wav")),
            Gdx.audio.newMusic(Gdx.files.internal("rain.mp3")), tarro
        );

        elementos = new ArrayList<>();
        elementos.add(tarro);
        elementos.add(lluvia);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.1f, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        // Dibujar el fondo y otros elementos en pantalla
        batch.draw(backgroundImage, 0, 0, camera.viewportWidth, camera.viewportHeight);

        // Mostrar vidas, puntuación, y high score
        font.draw(batch, "Vidas: " + tarro.getVidas(), 10, 470);
        font.draw(batch, "Puntuacion: " + tarro.getPuntos(), 10, 440);
        font.draw(batch, "High Score: " + game.getHigherScore(), 650, 470);

        // Actualizar y dibujar elementos
        for (Accionable elemento : elementos) {
            elemento.actualizar();
            elemento.dibujar(batch);
        }

        // Verificar colisiones y otros eventos
        if (!lluvia.verificarColision(tarro)) {
            game.setHigherScore(Math.max(game.getHigherScore(), tarro.getPuntos()));
            game.setScreen(new GameOverScreen(game));
            dispose();
        }

        // Control para pausar el juego
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            isPaused = !isPaused;  // Cambia el estado de pausa
            if (isPaused) {
                game.setScreen(new PausaScreen(game, this));  // Cambia a PausaScreen
                pause();  // Llama a pause() explícitamente para pausar la música
            }
        }

        batch.end();
    }

    @Override
    public void show() {
        // Este método se llama cuando la pantalla se muestra por primera vez.
        // Aquí puedes iniciar cualquier música de fondo o recursos que deben estar listos al comenzar.
        for (Accionable elemento : elementos) {
            if (elemento instanceof Lluvia) {
                ((Lluvia) elemento).continuar();
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        // Este método se llama cuando cambia el tamaño de la ventana.
        // Usualmente se usa para ajustar la cámara o la vista a nuevas dimensiones.
        camera.setToOrtho(false, width, height);
    }

    @Override
    public void pause() {
        // Pausar la música de lluvia
        for (Accionable elemento : elementos) {
            if (elemento instanceof Lluvia) {
                ((Lluvia) elemento).pausar(); // Llama al método pausar() en Lluvia para detener la música
            }
        }
    }

    @Override
    public void resume() {
        // Reanudar la música de lluvia
        for (Accionable elemento : elementos) {
            if (elemento instanceof Lluvia) {
                ((Lluvia) elemento).continuar(); // Llama al método continuar() en Lluvia para reanudar la música
            }
        }
    }

    @Override
    public void hide() {
        // Este método se llama cuando la pantalla actual ya no está activa.
        // Detenemos cualquier música o sonido y liberamos recursos si es necesario.
        for (Accionable elemento : elementos) {
            if (elemento instanceof Lluvia) {
                ((Lluvia) elemento).pausar();
            }
        }
    }

    @Override
    public void dispose() {
        // Este método se llama cuando la pantalla se destruye para liberar recursos.
        // Aquí liberamos texturas, sonidos y cualquier otro recurso que no se use.
        backgroundImage.dispose();
        for (Accionable elemento : elementos) {
            elemento.destruir();
        }
    }

	public void setPaused(boolean b) {
		// TODO Auto-generated method stub
		
	}
	
	public void resumeRainSound() {
	    for (Accionable elemento : elementos) {
	        if (elemento instanceof Lluvia) {
	            ((Lluvia) elemento).continuar();  // Llama al método continuar() de Lluvia para reanudar la música
	        }
	    }
	}


}
