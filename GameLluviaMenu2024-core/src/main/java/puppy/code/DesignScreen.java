package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class DesignScreen implements Screen {

    final GameLluviaMenu game;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Texture designTexture;
    private Texture frameTexture;
    private Sprite designSprite;
    private Sprite frameSprite;

    public DesignScreen(final GameLluviaMenu game) {
        this.game = game;
        this.batch = game.getBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        // Cargar la textura del marco
        frameTexture = new Texture(Gdx.files.internal("frame_example.png"));
        frameSprite = new Sprite(frameTexture);
        frameSprite.setPosition(0, 480 - frameSprite.getHeight());

        // Cargar la textura del diseño
        designTexture = new Texture(Gdx.files.internal("design_example.png"));
        designSprite = new Sprite(designTexture);

        // Hacer que el diseño sea un 90% del tamaño del marco
        float scale = 0.9f;
        float designWidth = frameSprite.getWidth() * scale;
        float designHeight = frameSprite.getHeight() * scale;

        designSprite.setSize(designWidth, designHeight);

        // Centrar el diseño dentro del marco
        float designX = frameSprite.getX() + (frameSprite.getWidth() - designWidth) / 2;
        float designY = frameSprite.getY() + (frameSprite.getHeight() - designHeight) / 2;
        designSprite.setPosition(designX, designY);
    }

    @Override
    public void show() { }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.5f, 0.5f, 0.5f, 1);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        frameSprite.draw(batch);  // Dibuja el marco detrás del diseño
        designSprite.draw(batch); // Dibuja el diseño ajustado sobre el marco
        batch.end();

        // Detectar si el toque ocurre dentro de los límites del sprite
        if (Gdx.input.isTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Ajuste para el eje Y

            // Verificar si el toque está dentro de los límites de designSprite
            if (designSprite.getBoundingRectangle().contains(touchX, touchY)) {
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
        }
    }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        designTexture.dispose();
        frameTexture.dispose();
    }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }
}
