package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {
    private final GameLluviaMenu game;
    private final OrthographicCamera camera;
    private final SpriteBatch batch;
    private final Texture backgroundImage;
    private final List<ElementoJuego> elementosJuego;
   // private Tarro tarro;

    public GameScreen() {
        this.game = GameLluviaMenu.getInstance();
        this.batch = game.getBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        backgroundImage = new Texture(Gdx.files.internal("newFondo.png"));

        elementosJuego = new ArrayList<>();
        Tarro tarro = new Tarro(Assets.bucketTexture, Assets.hurtSound, Assets.healthUpSound);
        elementosJuego.add(tarro);
        elementosJuego.add(new Lluvia(tarro));
    }

    @Override
    public void render(float delta) {
        // Limpiar la pantalla con un color de fondo
        ScreenUtils.clear(0, 0, 0.1f, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        // Iniciar el batch para dibujar los elementos
        batch.begin();
        batch.draw(backgroundImage, 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

     // Recorrer los elementos del juego y actualizarlos/dibujarlos
        for (ElementoJuego elemento : elementosJuego) {
            elemento.actualizarYDibujar(batch);
        }

        // Llamar a actualizar el estado del tarro
        Tarro tarro = (Tarro) elementosJuego.get(0); // Suponiendo que el tarro es el primer elemento
        tarro.actualizar(); // Actualizar lógica adicional como ralentización

        // Actualizar la estrategia de movimiento del tarro según las teclas presionadas
        if ((Gdx.input.isKeyPressed(Input.Keys.LEFT )) || (Gdx.input.isKeyPressed(Input.Keys.A ))) {
            tarro.setMovimiento(new MovimientoIzquierda());
        } else if ((Gdx.input.isKeyPressed(Input.Keys.RIGHT )) || (Gdx.input.isKeyPressed(Input.Keys.D ))) {
            tarro.setMovimiento(new MovimientoDerecha());
        } else {
            tarro.setMovimiento(null); // Sin movimiento si no hay teclas presionadas
        }

        // Mostrar los puntos y las vidas en pantalla
        game.getFont().draw(batch, "Puntos: " + tarro.getPuntos() + " | Vidas: " + tarro.getVidas(), 10, Constants.SCREEN_HEIGHT - 20);
        batch.end();

        // Verificar si las vidas se agotaron, cambiar a la pantalla de Game Over si es necesario
        if (tarro.getVidas() <= 0) {
            game.setScreen(new GameOverScreen());
            dispose();
        }
    }

    @Override
    public void dispose() {
        backgroundImage.dispose();
        for (ElementoJuego elemento : elementosJuego) {
            elemento.destruir();
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void show() {}
}
