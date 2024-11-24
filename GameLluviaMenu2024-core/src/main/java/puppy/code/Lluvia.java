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
        raindrop.x = MathUtils.random(0, Constants.SCREEN_WIDTH - 64);
        raindrop.y = Constants.SCREEN_HEIGHT;
        raindrop.width = 64;
        raindrop.height = 64;

        // Obtener el puntaje actual del tarro
        int puntos = tarro.getPuntos();
        int probabilidad = MathUtils.random(0, 100);
        FabricaDeGotitas fabricaSeleccionada;

        // Ajustar las probabilidades dinámicamente según el puntaje
        if (puntos >= 250) {
            // A partir de 250 puntos
            if (probabilidad < 5) { // 5% para gotita dorada
                fabricaSeleccionada = fabricaDeGotitas.get(1);
            } else if (probabilidad < 40) { // 35% para gotita azul
                fabricaSeleccionada = fabricaDeGotitas.get(0);
            } else if (probabilidad < 75) { // 35% para gotita roja
                fabricaSeleccionada = fabricaDeGotitas.get(2);
            } else if (probabilidad < 92) { // 17% para gotita nerff
                fabricaSeleccionada = fabricaDeGotitas.get(4);
            } else { // 8% para gotita vida
                fabricaSeleccionada = fabricaDeGotitas.get(3);
            }
        } else {
            // Probabilidades normales
            if (probabilidad < 3) { // 3% para gotita dorada
                fabricaSeleccionada = fabricaDeGotitas.get(1);
            } else if (probabilidad < 30) { // 27% para gotita azul
                fabricaSeleccionada = fabricaDeGotitas.get(0);
            } else if (probabilidad < 70) { // 40% para gotita roja
                fabricaSeleccionada = fabricaDeGotitas.get(2);
            } else if (probabilidad < 90) { // 20% para gotita verde
                fabricaSeleccionada = fabricaDeGotitas.get(3);
            } else { // 10% para gotita Nerff
                fabricaSeleccionada = fabricaDeGotitas.get(4);
            }
        }

        rainDropsPos.add(raindrop);
        rainDropsType.add(fabricaDeGotitas.indexOf(fabricaSeleccionada, true));
        lastDropTime = TimeUtils.nanoTime();
    }


    private float calcularVelocidadCaida() {
        int puntos = tarro.getPuntos();
        float velocidadBase = 300;
        float velocidadMaxima = velocidadBase * 2f;
        float incremento = (velocidadMaxima - velocidadBase) / 500; // Incrementa más lentamente con el puntaje
        return Math.min(velocidadBase + puntos * incremento, velocidadMaxima);
    }

    @Override
    public void actualizarMovimiento() {
        if (TimeUtils.nanoTime() - lastDropTime > 1000000000) {
            crearGotaDeLluvia();
        }

        for (Rectangle drop : rainDropsPos) {
            drop.y -= calcularVelocidadCaida() * Gdx.graphics.getDeltaTime(); // Velocidad dinámica
        }

        // Manejar colisiones
        for (int i = 0; i < rainDropsPos.size; i++) {
            Rectangle raindrop = rainDropsPos.get(i);
            if (tarro.colisionaConGota(raindrop)) {
                int tipo = rainDropsType.get(i);
                FabricaDeGotitas fabricaSeleccionada = fabricaDeGotitas.get(tipo);

                Efecto efecto = fabricaSeleccionada.crearEfecto();
                efecto.aplicarEfecto(tarro);

                // Reproducir sonido para gotas azules
                if (tipo == 0) {
                    Assets.dropSound.play();
                }

                rainDropsPos.removeIndex(i);
                rainDropsType.removeIndex(i);
                i--;
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
