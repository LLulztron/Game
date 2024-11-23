package puppy.code.gotitas;

public class FabricaDeGotitaAzul implements FabricaDeGotitas {
    @Override
    public Efecto crearEfecto() {
        return new EfectoPuntos();  // Crear el efecto de sumar puntos
    }
}
