package resources.sounds;

import java.io.*;
import javax.sound.sampled.*;

public class Sound {
    public static void play(String fileName) {
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(
                new BufferedInputStream(
                    Sound.class.getResourceAsStream("/resources/sounds/" + fileName)
                )
            );
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        } catch (Exception e) {
            System.err.println("Erro ao tocar som: " + e.getMessage());
        }
    }
}
