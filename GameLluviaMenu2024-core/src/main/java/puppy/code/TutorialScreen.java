package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class TutorialScreen implements Screen {

    private final GameLluviaMenu game;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Texture backgroundTexture, backButtonTexture;
    private Sprite backButtonSprite;

    public TutorialScreen() {
        this.game = GameLluviaMenu.getInstance();
        this.batch = game.getBatch();
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        // Cargar texturas
        backgroundTexture = new Texture(Gdx.files.internal("fondoTutorial.jpg"));
        backButtonTexture = new Texture(Gdx.files.internal("mainMenu.png"));

        // Crear sprite para el botón
        backButtonSprite = new Sprite(backButtonTexture);
        backButtonSprite.setPosition(600, 20);  // Botón de regreso en la esquina inferior derecha
        
        // Hacer el botón más grande
        backButtonSprite.setSize(backButtonSprite.getWidth() * 1.5f, backButtonSprite.getHeight() * 1.5f); // Aumentar tamaño
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.5f, 0.5f, 0.5f, 1);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        backButtonSprite.draw(batch);  // Botón de regreso
        batch.end();

        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            if (backButtonSprite.getBoundingRectangle().contains(touchPos.x, touchPos.y)) {
                game.setScreen(new MainMenuScreen());
                dispose();
            }
        }
    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        backButtonTexture.dispose();
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
	public void show() {
		// TODO Auto-generated method stub
		
	}
}