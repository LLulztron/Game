package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class Assets {

    // Definir las rutas de los recursos
    private static final String BUTTON_TEXTURE = "button.png";
    private static final String MAIN_MENU_TEXTURE = "mainMenu.png";
    private static final String PLAY_AGAIN_TEXTURE = "playAgain.png";
    private static final String BACKGROUND_TEXTURE = "newFondo.png";
    private static final String RAIN_MUSIC = "rain.mp3";

    // Texturas
    public static Texture buttonTexture;
    public static Texture mainMenuTexture;
    public static Texture playAgainTexture;
    public static Texture backgroundTexture;
    public static Texture gameBackgroundTexture;
    public static Texture loadingTexture;
    public static Texture dropTexture; // Gota buena
    public static Texture dropBadTexture; // Gota mala
    public static Texture dropIceTexture; // Nerf (ralentizar)
    public static Texture dropGPointTexture; // Gota dorada (golden point)
    public static Texture healthDropTexture; // Gota de salud
    public static Texture bucketTexture; // Tarro

    // Sonidos
    public static Music menuMusic;
    public static Music rainMusic;
    public static Sound gameOverSound;
    public static Sound dropSound;
    public static Sound hurtSound;
    public static Sound healthUpSound;

    public static void load() {
        try {
            // Cargar texturas
            buttonTexture = new Texture(Gdx.files.internal(BUTTON_TEXTURE));
            mainMenuTexture = new Texture(Gdx.files.internal(MAIN_MENU_TEXTURE));
            playAgainTexture = new Texture(Gdx.files.internal(PLAY_AGAIN_TEXTURE));
            gameBackgroundTexture = new Texture(Gdx.files.internal(BACKGROUND_TEXTURE));
            dropTexture = new Texture(Gdx.files.internal("drop.png")); // Gota buena
            dropBadTexture = new Texture(Gdx.files.internal("dropBad.png")); // Gota mala
            healthDropTexture = new Texture(Gdx.files.internal("healthDrp.png")); // Salud
            dropGPointTexture = new Texture(Gdx.files.internal("goldenPoint.png")); // GPoint
            dropIceTexture = new Texture(Gdx.files.internal("iceNerff.png")); // Ralentizar/Nerf
            bucketTexture = new Texture(Gdx.files.internal("bucket.png")); 
            loadingTexture = new Texture(Gdx.files.internal("loading.jpg")); 

            // Cargar sonidos
            menuMusic = Gdx.audio.newMusic(Gdx.files.internal("piano-loops.wav"));
            rainMusic = Gdx.audio.newMusic(Gdx.files.internal(RAIN_MUSIC));
            gameOverSound = Gdx.audio.newSound(Gdx.files.internal("gameover_sound.mp3"));
            dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
            hurtSound = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
            healthUpSound = Gdx.audio.newSound(Gdx.files.internal("HealthUp.mp3"));
            
        } catch (Exception e) {
            Gdx.app.error("Assets", "Error al cargar los recursos: " + e.getMessage());
        }
    }

    public static void dispose() {
        // Liberar recursos con verificación para evitar errores
        if (buttonTexture != null) buttonTexture.dispose();
        if (mainMenuTexture != null) mainMenuTexture.dispose();
        if (playAgainTexture != null) playAgainTexture.dispose();
        if (gameBackgroundTexture != null) gameBackgroundTexture.dispose();
        if (dropTexture != null) dropTexture.dispose();
        if (dropBadTexture != null) dropBadTexture.dispose();
        if (healthDropTexture != null) healthDropTexture.dispose();
        if (dropGPointTexture != null) dropGPointTexture.dispose();  // Liberar Gota dorada
        if (dropIceTexture != null) dropIceTexture.dispose();  // Liberar Gota nerff
        if (bucketTexture != null) bucketTexture.dispose();
        if (loadingTexture != null) loadingTexture.dispose();

        if (menuMusic != null) menuMusic.dispose();
        if (rainMusic != null) rainMusic.dispose();
        if (gameOverSound != null) gameOverSound.dispose();
        if (dropSound != null) dropSound.dispose();
        if (hurtSound != null) hurtSound.dispose();
        if (healthUpSound != null) healthUpSound.dispose();
    }
}
