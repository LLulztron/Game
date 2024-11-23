package puppy.code;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameLluviaMenu extends Game {
    private static GameLluviaMenu instance;

    private SpriteBatch batch;
    private BitmapFont font;

    private GameLluviaMenu() { }

    public static synchronized GameLluviaMenu getInstance() {
        if (instance == null) {
            instance = new GameLluviaMenu();
        }
        return instance;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont(); // Fuente predeterminada para el juego

        // Cargar recursos
        Assets.load();

        // Configurar la pantalla inicial
        setScreen(new MainMenuScreen());
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        Assets.dispose();
        if (font != null) font.dispose();
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public BitmapFont getFont() {
        return font;
    }
}