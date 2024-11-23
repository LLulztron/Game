package puppy.code.gotitas;

public class FabricaDeGotitaRoja implements FabricaDeGotitas {
    @Override
    public Efecto crearEfecto() {
        return new EfectoDaño();  // Crear el efecto de quitar vida
    }
}
