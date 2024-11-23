package puppy.code.gotitas;
import puppy.code.*;

public class EfectoPuntos implements Efecto {
    @Override
    public void aplicarEfecto(Tarro tarro) {
        tarro.sumarPuntos();
    }
}

