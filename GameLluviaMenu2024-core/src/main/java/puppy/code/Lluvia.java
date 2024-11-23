package puppy.code;

import puppy.code.gotitas.*;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Lluvia extends ElementoJuego {
    private final Array<Rectangle> rainDropsPos;
    private final Array<Integer> rainDropsType;  // Para almacenar el tipo de gota
    private final Array<FabricaDeGotitas> fabricaDeGotitas;
    private long lastDropTime;
    private final Tarro tarro;

    public Lluvia(Tarro tarro) {
        super(0, Constants.SCREEN_HEIGHT);
        this.tarro = tarro;

        rainDropsPos = new Array<>();
        rainDropsType = new Array<>();
        fabricaDeGotitas = new Array<>();

        // Inicializamos las fábricas para las gotas (azul, roja, verde, dorada, nerf)
        fabricaDeGotitas.add(new FabricaDeGotitaAzul()); // Gotita azul - Sumar puntos
        fabricaDeGotitas.add(new FabricaDeGotitaDorada()); // Gotita dorada - Sumar más puntos
        fabricaDeGotitas.add(new FabricaDeGotitaRoja()); // Gotita roja - Quitar vida
        fabricaDeGotitas.add(new FabricaDeGotitaVerde()); // Gotita verde - Dar vida
        fabricaDeGotitas.add(new FabricaDeGotitaNerff()); // Gotita Nerff - Ralentizar

        crearGotaDeLluvia(); // Crear la primera gota
        Assets.rainMusic.setLooping(true);
        Assets.rainMusic.play();
    }

    private void crearGotaDeLluvia() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, Constants.SCREEN_WIDTH - 64); // Posición aleatoria en el eje X
        raindrop.y = Constants.SCREEN_HEIGHT;
        raindrop.width = 64;
        raindrop.height = 64;

        // Probabilidad para seleccionar el tipo de gota (incluyendo la dorada con baja probabilidad)
        int probabilidad = MathUtils.random(0, 100); // Genera un número aleatorio entre 0 y 100
        FabricaDeGotitas fabricaSeleccionada;

        if (probabilidad < 5) { // 5% para gotita dorada
            fabricaSeleccionada = fabricaDeGotitas.get(1); // Gotita dorada (puntos extra)
        } else if (probabilidad < 33) { // 28% para gotita azul (puntos)
            fabricaSeleccionada = fabricaDeGotitas.get(0); // Gotita azul
        } else if (probabilidad < 66) { // 33% para gotita roja (daño)
            fabricaSeleccionada = fabricaDeGotitas.get(2); // Gotita roja
        } else if (probabilidad < 90) { // 24% para gotita verde (curación)
            fabricaSeleccionada = fabricaDeGotitas.get(3); // Gotita verde
        } else { // 10% para gotita Nerff (ralentizar)
            fabricaSeleccionada = fabricaDeGotitas.get(4); // Gotita Nerff (ralentizar)
        }

        // Crear el efecto usando la fábrica seleccionada
        Efecto efecto = fabricaSeleccionada.crearEfecto();
        rainDropsPos.add(raindrop); // Añadir la gota a la lista
        rainDropsType.add(fabricaDeGotitas.indexOf(fabricaSeleccionada, true)); // Guardar el tipo de gota
        lastDropTime = TimeUtils.nanoTime(); // Actualizar el tiempo de creación
    }

    @Override
    public void actualizarMovimiento() {
        if (TimeUtils.nanoTime() - lastDropTime > 1000000000) {
            crearGotaDeLluvia(); // Crear una nueva gota cada segundo
        }

        // Actualizar la posición de las gotas
        for (Rectangle drop : rainDropsPos) {
            drop.y -= 300 * Gdx.graphics.getDeltaTime(); // Velocidad de caída de las gotas
        }

        // Comprobar colisión de gotas con el tarro y aplicar los efectos correspondientes
        for (int i = 0; i < rainDropsPos.size; i++) {
            Rectangle raindrop = rainDropsPos.get(i);
            if (tarro.colisionaConGota(raindrop)) {
                // Obtener el tipo de gota y su respectiva fábrica
                int tipo = rainDropsType.get(i);
                FabricaDeGotitas fabricaSeleccionada = fabricaDeGotitas.get(tipo);

                // Crear y aplicar el efecto correspondiente
                Efecto efecto = fabricaSeleccionada.crearEfecto();
                efecto.aplicarEfecto(tarro); // Aplicar el efecto al tarro

                // Eliminar la gota de la pantalla después de la colisión
                rainDropsPos.removeIndex(i);
                rainDropsType.removeIndex(i);
                i--; // Ajustar el índice después de eliminar la gota
            }
        }
    }

    @Override
    public void dibujar(SpriteBatch batch) {
        // Dibujar las gotas en la pantalla
        for (int i = 0; i < rainDropsPos.size; i++) {
            Texture textura;
            // Asignamos la textura de acuerdo al tipo de gota
            switch (rainDropsType.get(i)) {
                case 0:
                    textura = Assets.dropTexture;  // Textura para gotita azul
                    break;
                case 1:
                    textura = Assets.dropGPointTexture;  // Textura para gotita dorada (más puntos)
                    break;
                case 2:
                    textura = Assets.dropBadTexture;  // Textura para gotita roja
                    break;
                case 3:
                    textura = Assets.healthDropTexture;  // Textura para gotita verde
                    break;
                case 4:
                    textura = Assets.dropIceTexture;  // Textura para gotita Nerff (ralentización)
                    break;
                default:
                    textura = Assets.dropTexture;  // Textura por defecto
                    break;
            }
            Rectangle raindrop = rainDropsPos.get(i);
            batch.draw(textura, raindrop.x, raindrop.y);  // Dibujar la gota
        }
    }

    @Override
    public void destruir() {
        // Liberar los recursos de la música de la lluvia cuando se destruye la clase
  
    }

	@Override
	public void actualizar() {
		// TODO Auto-generated method stub
		
	}
}
