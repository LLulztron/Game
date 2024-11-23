package puppy.code.gotitas;

public class FabricaDeGotitaDorada implements FabricaDeGotitas {
    @Override
    public Efecto crearEfecto() {
        return new EfectoPuntosDorado();  // Crear el efecto de sumar puntos
    }
}
