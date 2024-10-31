package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameOverScreen implements Screen {
    private final GameLluviaMenu game;
    private SpriteBatch batch;
    private BitmapFont font;
    private OrthographicCamera camera;
    private Texture backgroundTexture;
    private Sprite backgroundSprite;
    private Sound gameOverSound;

    // Texturas y sprites de los botones y texto de Game Over
    private Texture playAgainTexture;
    private Texture mainMenuTexture;
    private Sprite playAgainButton;
    private Sprite mainMenuButton;
    private Texture gameOverTextTexture;
    private Sprite gameOverTextSprite; // Sprite para el texto "Game Over"

    public GameOverScreen(final GameLluviaMenu game) {
        this.game = game;
        this.batch = game.getBatch();
        this.font = game.getFont();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 600, 480);

        // Cargar la textura del fondo
        backgroundTexture = new Texture(Gdx.files.internal("gameOver.jpeg"));
        backgroundSprite = new Sprite(backgroundTexture);
        backgroundSprite.setSize(600, 480);

        // Cargar el sonido de Game Over
        gameOverSound = Gdx.audio.newSound(Gdx.files.internal("gameover_sound.mp3"));

        // Cargar texturas de botones
        playAgainTexture = new Texture(Gdx.files.internal("playAgain.png"));
        mainMenuTexture = new Texture(Gdx.files.internal("mainMenu.png"));

        // Crear sprites de botones y posicionarlos en la esquina inferior izquierda
        playAgainButton = new Sprite(playAgainTexture);
        playAgainButton.setPosition(20, 20);  // Posicionar en la esquina inferior izquierda

        mainMenuButton = new Sprite(mainMenuTexture);
        mainMenuButton.setPosition(20, 25 + playAgainButton.getHeight());  // Posicionar encima del botón "Volver a Jugar"

        // Cargar y crear el sprite de "Game Over"
        gameOverTextTexture = new Texture(Gdx.files.internal("gameOverText.png"));
        gameOverTextSprite = new Sprite(gameOverTextTexture);
        gameOverTextSprite.setPosition( 160 , (600 - gameOverTextSprite.getWidth()) / 2);
    }

    @Override
    public void show() {
        // Reproducir el sonido de Game Over
        gameOverSound.play();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        backgroundSprite.draw(batch);

        // Dibujar el texto "Game Over"
        gameOverTextSprite.draw(batch);

        // Dibujar botones
        playAgainButton.draw(batch);
        mainMenuButton.draw(batch);

        batch.end();

        // Detectar si se ha tocado un botón
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos); // Convertir a coordenadas de juego

            // Verificar si el toque está dentro del botón "Volver a Jugar"
            if (playAgainButton.getBoundingRectangle().contains(touchPos.x, touchPos.y)) {
                game.setScreen(new GameScreen(game)); // Volver a jugar
                dispose();
            }

            // Verificar si el toque está dentro del botón "Menú Principal"
            if (mainMenuButton.getBoundingRectangle().contains(touchPos.x, touchPos.y)) {
                game.setScreen(new MainMenuScreen(game)); // Volver al menú principal
                dispose();
            }
        }
    }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        gameOverSound.dispose();
        playAgainTexture.dispose();
        mainMenuTexture.dispose();
        gameOverTextTexture.dispose();  // Liberar la textura del texto "Game Over"
    }
}