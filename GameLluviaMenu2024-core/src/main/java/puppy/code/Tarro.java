/*package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;


public class Tarro {
	   private Rectangle bucket;
	   private Texture bucketImage;
	   private Sound sonidoHerido;
	   private int vidas = 3;
	   private int puntos = 0;
	   private int velx = 400;
	   private boolean herido = false;
	   private int tiempoHeridoMax=50;
	   private int tiempoHerido;
	   
	   
	   public Tarro(Texture tex, Sound ss) {
		   bucketImage = tex;
		   sonidoHerido = ss;
	   }
	   
		public int getVidas() {
			return vidas;
		}
	
		public int getPuntos() {
			return puntos;
		}
		public Rectangle getArea() {
			return bucket;
		}
		public void sumarPuntos(int pp) {
			puntos+=pp;
		}
		
	
	   public void crear() {
		      bucket = new Rectangle();
		      bucket.x = 800 / 2 - 64 / 2;
		      bucket.y = 20;
		      bucket.width = 64;
		      bucket.height = 64;
	   }
	   public void dañar() {
		  vidas--;
		  herido = true;
		  tiempoHerido=tiempoHeridoMax;
		  sonidoHerido.play();
	   }
	   public void aumentarVida() {
		   vidas++;
	   }
	   
	   public void dibujar(SpriteBatch batch) {
		 if (!herido)  
		   batch.draw(bucketImage, bucket.x, bucket.y);
		 else {
		
		   batch.draw(bucketImage, bucket.x, bucket.y+ MathUtils.random(-5,5));
		   tiempoHerido--;
		   if (tiempoHerido<=0) herido = false;
		 }
	   } 
	   
	   
	   public void actualizarMovimiento() { 
		   //movimiento desde teclado
		   if((Gdx.input.isKeyPressed(Input.Keys.A)) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= velx * Gdx.graphics.getDeltaTime();//Para usar las flechitas, reemplazar A por LEFT
		   if((Gdx.input.isKeyPressed(Input.Keys.D)) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += velx * Gdx.graphics.getDeltaTime();//lo mismo pero D por RIGHT 	
		   
		   // que no se salga de los bordes izquierda y derecha
		   if(bucket.x < 0) bucket.x = 0;	
		   if(bucket.x > 800 - 64) bucket.x = 800 - 64;
	   }
	    

	public void destruir() {
		    bucketImage.dispose();
	   }
	
   public boolean estaHerido() {
	   return herido;
   }
	   
}*/

package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;

public class Tarro extends ElementoJuego {
    private Rectangle bucket;
    private Texture bucketImage;
    private Sound sonidoHerido;
    private int vidas = 3;
    private int puntos = 0;
    private int velx = 400;
    private boolean herido = false;
    private int tiempoHeridoMax = 50;
    private int tiempoHerido;

    public Tarro(Texture tex, Sound ss) {
        super(800 / 2 - 64 / 2, 20); // Llama al constructor de ElementoJuego
        bucketImage = tex;
        sonidoHerido = ss;
        bucket = new Rectangle(posX, posY, 64, 64);
    }

    public int getVidas() {
        return vidas;
    }

    public int getPuntos() {
        return puntos;
    }

    public Rectangle getArea() {
        return bucket;
    }

    public void sumarPuntos(int pp) {
        puntos += pp;
    }

    public void dañar() {
        vidas--;
        herido = true;
        tiempoHerido = tiempoHeridoMax;
        sonidoHerido.play();
    }

    public void aumentarVida() {
        vidas++;
    }

    @Override
    public void dibujar(SpriteBatch batch) {
        if (!herido)
            batch.draw(bucketImage, bucket.x, bucket.y);
        else {
            batch.draw(bucketImage, bucket.x, bucket.y + MathUtils.random(-5, 5));
            tiempoHerido--;
            if (tiempoHerido <= 0) herido = false;
        }
    }

    @Override
    public void actualizarMovimiento() {
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT))
            bucket.x -= velx * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            bucket.x += velx * Gdx.graphics.getDeltaTime();

        if (bucket.x < 0) bucket.x = 0;
        if (bucket.x > 800 - 64) bucket.x = 800 - 64;
    }

    @Override
    public void destruir() {
        bucketImage.dispose();
    }

	public void crear() {
		// TODO Auto-generated method stub
		
	}
}
