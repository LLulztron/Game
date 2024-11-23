package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameOverScreen implements Screen {
    private final GameLluviaMenu game;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Texture backgroundTexture, playAgainTexture, mainMenuTexture;
    private Sprite playAgainButton, mainMenuButton;

    public GameOverScreen() {
        this.game = GameLluviaMenu.getInstance();
        this.batch = game.getBatch();
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        // Cargar recursos
        backgroundTexture = new Texture(Gdx.files.internal("gameOver.jpeg"));
        playAgainTexture = new Texture(Gdx.files.internal("playAgain.png"));
        mainMenuTexture = new Texture(Gdx.files.internal("mainMenu.png"));

        // Crear botones
        playAgainButton = new Sprite(playAgainTexture);
        mainMenuButton = new Sprite(mainMenuTexture);

        // Posicionarlos
        playAgainButton.setPosition(Constants.SCREEN_WIDTH / 2 - playAgainButton.getWidth() / 2, 300);
        mainMenuButton.setPosition(Constants.SCREEN_WIDTH / 2 - mainMenuButton.getWidth() / 2, 200);

        // Aumentar tamaño de los botones
        playAgainButton.setSize(playAgainButton.getWidth() * 1.5f, playAgainButton.getHeight() * 1.5f); // Aumentar un 50%
        mainMenuButton.setSize(mainMenuButton.getWidth() * 1.5f, mainMenuButton.getHeight() * 1.5f); // Aumentar un 50%
    }

    @Override
    public void show() {
        System.out.println("Pantalla de Game Over mostrada.");
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        playAgainButton.draw(batch);  // Dibujar el botón "Reintentar"
        mainMenuButton.draw(batch);  // Dibujar el botón "Menú Principal"
        batch.end();

        // Detectar interacción con los botones
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            if (playAgainButton.getBoundingRectangle().contains(touchPos.x, touchPos.y)) {
                game.setScreen(new GameScreen());
                dispose();
            } else if (mainMenuButton.getBoundingRectangle().contains(touchPos.x, touchPos.y)) {
                game.setScreen(new MainMenuScreen());
                dispose();
            }
        }
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        playAgainButton.getTexture().dispose();
        mainMenuButton.getTexture().dispose();
    }
}