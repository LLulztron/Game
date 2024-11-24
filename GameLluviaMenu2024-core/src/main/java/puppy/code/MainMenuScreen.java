package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Vector3;

public class MainMenuScreen implements Screen {
    private final GameLluviaMenu game;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    
    // Botones
    private Sprite newGameButton;
    private Sprite tutorialButton;
    private Sprite themesButton;
    private Sprite backgroundSprite;
    
    public MainMenuScreen() {
        this.game = GameLluviaMenu.getInstance();
        this.batch = game.getBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        // Cargar las texturas para los botones y fondo
        Texture newGameTexture = new Texture(Gdx.files.internal("button.png"));
        Texture tutorialTexture = new Texture(Gdx.files.internal("temas.jpg"));
        Texture themesTexture = new Texture(Gdx.files.internal("temas.jpg"));
        Texture backgroundTexture = new Texture(Gdx.files.internal("clouds.jpg"));

        // Crear los sprites para los botones
        newGameButton = new Sprite(newGameTexture);
        tutorialButton = new Sprite(tutorialTexture);
        themesButton = new Sprite(themesTexture);
        backgroundSprite = new Sprite(backgroundTexture);
        
        // Posiciones de los botones
        newGameButton.setPosition(Constants.SCREEN_WIDTH / 2 - newGameButton.getWidth() / 2, 250); // Botón "Nuevo Juego"
        tutorialButton.setPosition(Constants.SCREEN_WIDTH / 2 - tutorialButton.getWidth() / 2, 180); // Botón "Tutorial"
        themesButton.setPosition(Constants.SCREEN_WIDTH / 2 - themesButton.getWidth() / 2, 110); // Botón "Temas"
    }

    @Override
    public void render(float delta) {
        // Limpiar la pantalla con un color
        ScreenUtils.clear(0, 0, 0.2f, 1);  
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(backgroundSprite, 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT); // Fondo
        newGameButton.draw(batch); // Botón "Nuevo Juego"
        tutorialButton.draw(batch); // Botón "Tutorial"
        themesButton.draw(batch); // Botón "Temas"
        batch.end();

        // Detectar toque para interactuar con los botones
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos); // Convertir coordenadas táctiles a coordenadas de pantalla
            
            if (newGameButton.getBoundingRectangle().contains(touchPos.x, touchPos.y)) {
                game.setScreen(new GameScreen());
                dispose();
            } else if (tutorialButton.getBoundingRectangle().contains(touchPos.x, touchPos.y)) {
                game.setScreen(new TutorialScreen());
                dispose();
            } else if (themesButton.getBoundingRectangle().contains(touchPos.x, touchPos.y)) {
                game.setScreen(new LoadingScreen());
                dispose();
            }
        }

        // Detectar la tecla "ESCAPE" o "SPACE" para pausar el juego
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE) || Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.SPACE)) {
            game.setScreen(new PausaScreen());  // Cambiar a pantalla de pausa
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);  // Ajustar la cámara para la nueva resolución
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void show() {
        System.out.println("Pantalla del menú principal mostrada.");
        if (Assets.menuMusic != null) {
            Assets.menuMusic.play(); // Reproducir música
        }
    }

    @Override
    public void hide() {
        System.out.println("Pantalla del menú principal oculta.");
        if (Assets.menuMusic != null) {
            Assets.menuMusic.stop(); // Detener música
        }
    }

    @Override
    public void dispose() {
        // Detener música al salir de la pantalla, si es necesario
        if (Assets.menuMusic != null) {
            Assets.menuMusic.stop();
        }
        // Liberar otros recursos
        newGameButton.getTexture().dispose();
        tutorialButton.getTexture().dispose();
        themesButton.getTexture().dispose();
        backgroundSprite.getTexture().dispose();
    }

}