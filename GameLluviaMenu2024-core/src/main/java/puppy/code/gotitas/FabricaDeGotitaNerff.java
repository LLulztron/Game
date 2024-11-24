package puppy.code.gotitas;

public class FabricaDeGotitaNerff implements FabricaDeGotitas {

    private static final float DURACION_NERF = 2f;  // Duración del efecto de ralentización (en segundos)

    @Override
    public Efecto crearEfecto() {
        // Crear y devolver el efecto de ralentización con la duración establecida
        return new EfectoNerf(DURACION_NERF);
    }
}
