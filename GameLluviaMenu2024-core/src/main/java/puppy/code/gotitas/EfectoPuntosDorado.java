package puppy.code.gotitas;
import puppy.code.*;

public class EfectoPuntosDorado implements Efecto {
    @Override
    public void aplicarEfecto(Tarro tarro) {
        tarro.GoldenPoint();
    }
}

