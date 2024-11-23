package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class PausaScreen implements Screen {
    private final GameLluviaMenu game;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Texture buttonTexture, backgroundTexture;
    private Sprite buttonSprite;

    public PausaScreen() {
        this.game = GameLluviaMenu.getInstance();
        this.batch = game.getBatch();
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        // Cargar texturas
        buttonTexture = new Texture(Gdx.files.internal("mainMenu.png"));
        backgroundTexture = new Texture(Gdx.files.internal("BackgroundPausa.jpg"));
        
        // Crear sprite para el botón
        buttonSprite = new Sprite(buttonTexture);
        buttonSprite.setPosition(Constants.SCREEN_WIDTH - 100, 20);
        
        // Hacer el botón un poco más grande
        buttonSprite.setSize(buttonSprite.getWidth() * 1.5f, buttonSprite.getHeight() * 1.5f); // Aumentar tamaño en un 50%
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 1, 1);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        buttonSprite.draw(batch);  // Dibujar el botón más grande
        batch.end();

        // Detectar la tecla "ESCAPE" o "SPACE" para reanudar el juego
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE) || Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.SPACE)) {
            game.setScreen(new GameScreen());  // Reanudar el juego
        }

        if (Gdx.input.isTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

            // Detectar si se toca el botón de "Menú Principal"
            if (touchX >= Constants.SCREEN_WIDTH - 100 && touchX <= Constants.SCREEN_WIDTH - 100 + buttonSprite.getWidth() &&
                touchY <= 60 && touchY >= 60 - buttonSprite.getHeight()) {
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
        buttonTexture.dispose();
        backgroundTexture.dispose();
    }

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
}
