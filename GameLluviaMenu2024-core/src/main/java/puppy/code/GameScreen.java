/*package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
    private Music rainMusic; // Variable para el sonido de lluvia

    public GameScreen(final GameLluviaMenu game) {
        this.game = game;
        this.batch = game.getBatch();
        this.font = game.getFont();
        
        backgroundImage = new Texture(Gdx.files.internal("Fondo.jpg")); // Ajusta el nombre del archivo de imagen

        Sound hurtSound = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
        tarro = new Tarro(new Texture(Gdx.files.internal("bucket.png")), hurtSound);

        Texture gota = new Texture(Gdx.files.internal("drop.png"));
        Texture gotaMala = new Texture(Gdx.files.internal("dropBad.png"));
        Texture gotaBuff = new Texture(Gdx.files.internal("healthDrp.png"));
        Sound dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3")); // Cambia a una variable de clase
        lluvia = new Lluvia(gota, gotaMala, gotaBuff, dropSound, rainMusic);
        
        // Camera setup
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        
        // Tarro and lluvia creation
        tarro.crear();
        lluvia.crear();
    }

    @Override
    public void render(float delta) {
        // Limpiar la pantalla con un color específico
        ScreenUtils.clear(0, 0, 0.1f, 1);
        
        // Actualizar la cámara
        camera.update();
        
        // Configurar la matriz de proyección del batch
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // Dibujar el fondo
        batch.draw(backgroundImage, 0, 0, camera.viewportWidth, camera.viewportHeight);

        // Dibujar textos
        font.draw(batch, "Gotas totales: " + tarro.getPuntos(), 5, 475);
        font.draw(batch, "Vidas : " + tarro.getVidas(), 670, 475);
        font.draw(batch, "HighScore : " + game.getHigherScore(), camera.viewportWidth / 2 - 50, 475);
        
        // Lógica para alternar entre pausa y juego
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) { // Cambiar a otra tecla si es necesario
            isPaused = !isPaused; // Cambia el estado de pausa
            if (isPaused) {
                setPaused(true); // Llama al método setPaused
                game.setScreen(new PausaScreen(game, this));
            }
        }

        if (!isPaused) {
            // Movimiento del tarro desde teclado
            tarro.actualizarMovimiento();
            // Caída de la lluvia 
            if (!lluvia.actualizarMovimiento(tarro)) {
                // Actualizar HighScore
                if (game.getHigherScore() < tarro.getPuntos())
                    game.setHigherScore(tarro.getPuntos());  
                // Ir a la ventana de fin de juego y destruir la actual
                game.setScreen(new GameOverScreen(game));
                dispose();
            }
            
            // Dibujar el tarro y la lluvia
            tarro.dibujar(batch);
            lluvia.actualizarDibujoLluvia(batch);
        }
        
        batch.end();
    }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void show() {
        // Continuar con el sonido de lluvia
        continuarRainSound(); // Asegúrate de llamar al método para iniciar el sonido
    }

    @Override
    public void hide() { }

    @Override
    public void pause() {
        pausarRainSound(); // Pausa el sonido de lluvia al pausar
        game.setScreen(new PausaScreen(game, this)); 
    }

    @Override
    public void resume() { }

    @Override
    public void dispose() {
        tarro.destruir();
        lluvia.destruir();
        backgroundImage.dispose(); // Liberar la textura del fondo
        rainMusic.dispose(); // Liberar el sonido de lluvia
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public void pausarRainSound() {
        if (rainMusic.isPlaying()) {
            rainMusic.pause(); // Pausar el sonido de lluvia
        }
    }

    public void continuarRainSound() {
        if (!rainMusic.isPlaying()) {
            rainMusic.setLooping(true); // Hacer que el sonido se repita
            rainMusic.play(); // Reanudar el sonido de lluvia
        }
    }

    public void stopRainSound() {
        if (rainMusic.isPlaying()) {
            rainMusic.stop(); // Detener el sonido de lluvia
        }
    }

    public void resumeRainSound() {
        if (!rainMusic.isPlaying()) {
            rainMusic.play(); // Reanudar el sonido de lluvia
        }
    }
}*/

package puppy.code;

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
}
