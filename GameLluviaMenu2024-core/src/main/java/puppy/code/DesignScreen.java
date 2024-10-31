package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class DesignScreen implements Screen {

    final GameLluviaMenu game;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Texture backgroundTexture;
    private Sprite frameSprite;
    private Sprite designSprite;
    private Sprite backButtonSprite;  // Sprite para el botón de regreso al inicio
    private Music designScreenSong;    // Música de fondo para la pantalla de diseños

    public DesignScreen(final GameLluviaMenu game) {
        this.game = game;
        this.batch = game.getBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 600, 480);

        // Cargar la textura del fondo
        backgroundTexture = new Texture(Gdx.files.internal("FondoTemas.jpg"));
        
        // Inicializar los sprites de diseño y marco
        Texture frameTexture = new Texture(Gdx.files.internal("frameDesing.png"));
        Texture designTexture = new Texture(Gdx.files.internal("design.png"));
        
        System.out.println(Gdx.files.internal("design.png").exists());
        
        frameSprite = new Sprite(frameTexture);
        designSprite = new Sprite(designTexture);
        
        // Posiciona el frame en la esquina superior izquierda
        frameSprite.setPosition(0, Gdx.graphics.getHeight() - frameSprite.getHeight());

        // Redimensionar designSprite para que se ajuste dentro de frameSprite
        float designScale = Math.min(
            frameSprite.getWidth() / designSprite.getWidth(),
            frameSprite.getHeight() / designSprite.getHeight()
        );
        designSprite.setSize(designSprite.getWidth() * designScale, designSprite.getHeight() * designScale);

        // Posicionar designSprite dentro del frame, centrado
        designSprite.setPosition(
            frameSprite.getX() + (frameSprite.getWidth() - designSprite.getWidth()) / 2,
            frameSprite.getY() + (frameSprite.getHeight() - designSprite.getHeight()) / 2
        );

        // Cargar la textura del botón de regreso
        Texture backButtonTexture = new Texture(Gdx.files.internal("mainMenu.png")); // Asegúrate de que el archivo exista
        backButtonSprite = new Sprite(backButtonTexture);
        
        // Posicionar el botón en la esquina inferior derecha
        backButtonSprite.setPosition(Gdx.graphics.getWidth() - backButtonSprite.getWidth() - 10, 10);

        // Cargar la música de fondo
        designScreenSong = Gdx.audio.newMusic(Gdx.files.internal("DesingScreenSong.mp3"));
        designScreenSong.setLooping(true);  // Configura la música en bucle
    }

    @Override
    public void show() {
        // Reproducir la música cuando se muestra la pantalla de diseños
        designScreenSong.play();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.5f, 0.5f, 0.5f, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        // Dibujar el fondo de la pantalla de diseños
        batch.draw(backgroundTexture, 0, 0, 600, 480);
        // Dibujar el marco detrás del diseño
        frameSprite.draw(batch);
        // Dibujar el diseño ajustado dentro del marco
        designSprite.draw(batch);
        // Dibujar el botón de regreso
        backButtonSprite.draw(batch);
        batch.end();

        // Detectar si el toque ocurre dentro de los límites del botón o del diseño
        if (Gdx.input.isTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Ajuste para el eje Y

            // Verificar si el toque está dentro de los límites del botón de regreso
            if (backButtonSprite.getBoundingRectangle().contains(touchX, touchY)) {
                game.setScreen(new MainMenuScreen(game));  // Regresar al menú principal
                dispose();
            }

            // Verificar si el toque está dentro de los límites de designSprite
            if (designSprite.getBoundingRectangle().contains(touchX, touchY)) {
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
        }
    }

    @Override
    public void hide() {
        // Pausar la música cuando se oculta la pantalla
        designScreenSong.pause();
    }

    @Override
    public void dispose() {
        // Liberar todos los recursos cuando se cierra la pantalla
        backgroundTexture.dispose();
        frameSprite.getTexture().dispose();
        designSprite.getTexture().dispose();
        backButtonSprite.getTexture().dispose();
        designScreenSong.dispose();
    }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }
}