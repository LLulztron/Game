package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class LoadingScreen implements Screen {
    private final GameLluviaMenu game;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Texture loadingTexture;
    private float elapsedTime;

    public LoadingScreen() {
        this.game = GameLluviaMenu.getInstance();
        this.batch = game.getBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        // Textura de carga
        loadingTexture = new Texture(Gdx.files.internal("loading.jpg")); 
        elapsedTime = 0;
    }

    @Override
    public void show() {
        System.out.println("Pantalla de carga mostrada.");
    }

    @Override
    public void render(float delta) {
        elapsedTime += delta;

        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(loadingTexture, Constants.SCREEN_WIDTH / 2 - loadingTexture.getWidth() / 2, 
                   Constants.SCREEN_HEIGHT / 2 - loadingTexture.getHeight() / 2);
        batch.end();

        // Simula tiempo de carga (3 segundos)
        if (elapsedTime > 1.2) {
            game.setScreen(new DesignScreen(game));
            dispose();
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
        loadingTexture.dispose();
    }
}