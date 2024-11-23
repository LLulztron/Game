package puppy.code.gotitas;

public class FabricaDeGotitaVerde implements FabricaDeGotitas {
    @Override
    public Efecto crearEfecto() {
        return new EfectoCuracion();  // Crear el efecto de curar vida
    }
}
