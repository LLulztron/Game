package puppy.code.gotitas;
import puppy.code.*;

public class EfectoDaño implements Efecto {
    @Override
    public void aplicarEfecto(Tarro tarro) {
        tarro.quitarVida();
    }
}