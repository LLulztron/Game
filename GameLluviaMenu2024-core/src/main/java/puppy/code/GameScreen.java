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

    private Tarro tarro;
    private Lluvia lluvia;

    public GameScreen(final GameLluviaMenu game) {
        this.game = game;
        this.batch = game.getBatch();
        this.font = game.getFont();

        backgroundImage = new Texture(Gdx.files.internal("Fondo.jpg"));

        tarro = new Tarro(new Texture(Gdx.files.internal("bucket.png")), 
                          Gdx.audio.newSound(Gdx.files.internal("hurt.ogg")));
        lluvia = new Lluvia(
            new Texture(Gdx.files.internal("drop.png")),
            new Texture(Gdx.files.internal("dropBad.png")),
            new Texture(Gdx.files.internal("healthDrp.png")),
            Gdx.audio.newSound(Gdx.files.internal("drop.wav")),
            Gdx.audio.newSound(Gdx.files.internal("buffDrop.mp3")),  // Sonido de gota buff
            Gdx.audio.newMusic(Gdx.files.internal("rain.mp3")),
            tarro
        );

        elementos = new ArrayList<>();
        elementos.add(tarro);
        elementos.add(lluvia);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 600, 500);  
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.1f, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(backgroundImage, 0, 0, camera.viewportWidth, camera.viewportHeight);

        font.draw(batch, "Vidas: " + tarro.getVidas(), 5, 490);
        font.draw(batch, "Puntuacion: " + tarro.getPuntos(), 250, 490);
        font.draw(batch, "High Score: " + game.getHigherScore(), 475, 490);

        for (Accionable elemento : elementos) {
            elemento.actualizar();
            elemento.dibujar(batch);
        }

        if (!lluvia.verificarColision(tarro)) {
            game.setHigherScore(Math.max(game.getHigherScore(), tarro.getPuntos()));
            game.setScreen(new GameOverScreen(game));
            dispose();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            isPaused = !isPaused;
            if (isPaused) {
                game.setScreen(new PausaScreen(game, this));
                pause();
            }
        }

        batch.end();
    }

    @Override
    public void show() {
        for (Accionable elemento : elementos) {
            if (elemento instanceof Lluvia) {
                ((Lluvia) elemento).continuar();
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }

    @Override
    public void pause() {
        for (Accionable elemento : elementos) {
            if (elemento instanceof Lluvia) {
                ((Lluvia) elemento).pausar();
            }
        }
    }

    @Override
    public void resume() {
        for (Accionable elemento : elementos) {
            if (elemento instanceof Lluvia) {
                ((Lluvia) elemento).continuar();
            }
        }
    }

    @Override
    public void hide() {
        for (Accionable elemento : elementos) {
            if (elemento instanceof Lluvia) {
                ((Lluvia) elemento).pausar();
            }
        }
    }

    @Override
    public void dispose() {
        backgroundImage.dispose();
        for (Accionable elemento : elementos) {
            elemento.destruir();
        }
    }

    public void setPaused(boolean b) {}

    public void resumeRainSound() {
        for (Accionable elemento : elementos) {
            if (elemento instanceof Lluvia) {
                ((Lluvia) elemento).continuar();
            }
        }
    }
}