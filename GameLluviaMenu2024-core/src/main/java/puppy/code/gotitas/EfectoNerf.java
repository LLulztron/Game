package puppy.code.gotitas;

import puppy.code.Tarro;

public class EfectoNerf implements Efecto {

    private final float duracion;  // Duración del efecto de ralentización

    // Constructor para inicializar la duración del efecto
    public EfectoNerf(float duracion) {
        this.duracion = duracion;
    }

    @Override
    public void aplicarEfecto(Tarro tarro) {
        // Aplicamos el efecto de ralentización al tarro
        tarro.aplicarRalentizacion(duracion);
    }
}
