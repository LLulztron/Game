package puppy.code.gotitas;
import puppy.code.*;

public class EfectoCuracion implements Efecto {
    @Override
    public void aplicarEfecto(Tarro tarro) {
        tarro.sumarVida();
    }
}