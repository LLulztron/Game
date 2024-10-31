package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class PausaScreen implements Screen {

    private final GameLluviaMenu game;
    private GameScreen juego;
    private SpriteBatch batch;	   
    private BitmapFont font;
    private OrthographicCamera camera;
    private Texture buttonTexture; // Textura del botón
    private Sprite buttonSprite; // Sprite del botón

    public PausaScreen(final GameLluviaMenu game, GameScreen juego) {
        this.game = game;
        this.juego = juego;
        this.batch = game.getBatch();
        this.font = game.getFont();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        // Cargar la textura y crear el sprite del botón
        buttonTexture = new Texture(Gdx.files.internal("button_back.png")); // Asegúrate de tener esta textura
        buttonSprite = new Sprite(buttonTexture);
        buttonSprite.setPosition(25, 100); // Posición del botón
    }

    @Override
    public void show() {
        // Detener el sonido al mostrar la pantalla de pausa
        juego.resumeRainSound(); // Llama al método que detiene el sonido
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 1.0f, 0.5f);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        font.draw(batch, "Juego en Pausa", 100, 150);
        font.draw(batch, "Toca en cualquier lado para continuar !!!", 100, 100);
        buttonSprite.draw(batch);  // Dibuja el botón de regreso
        batch.end();

        if (Gdx.input.isTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (buttonSprite.getBoundingRectangle().contains(touchX, touchY)) {
                game.setScreen(new MainMenuScreen(game));  // Opción de regresar al menú principal
                dispose();  // Libera recursos de la pantalla de pausa
            } else {
                game.setScreen(juego);  // Vuelve al juego
                juego.setPaused(false); // Asegura que el juego no esté en pausa
                juego.resume();         // Llama a resume() explícitamente para reanudar la música
                dispose();  // Libera recursos de la pantalla de pausa
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
        buttonTexture.dispose(); // Libera la textura del botón
    }
}
