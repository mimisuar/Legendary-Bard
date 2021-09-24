package msuarez.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.ArrayMap;

/**
 * Created by APCS on 1/6/2017.
 */

public class DynamicSound {
    private static float[] scale = equalTemperament();
    public static ResManager resman;
    public static ArrayMap<String, String> instrumentAlias = new ArrayMap<String, String>();

    public static void loadInstruments() {
        FileHandle instrumentsDir = Gdx.files.internal("sfx/dynasound");
        if (instrumentsDir.isDirectory()) {
            FileHandle[] instruments = instrumentsDir.list();

            for (FileHandle instrument : instruments) {
                if (instrument.name().contains(".txt")) { continue; }

                resman.manager.load(instrument.path(), Sound.class);
                instrumentAlias.put(instrument.nameWithoutExtension(), instrument.path());
            }
        }

        resman.manager.finishLoading();
    }

    public static float[] equalTemperament() {
        float[] tmp = new float[12];

        for (int i = 0; i < tmp.length; i++) {
            tmp[i] = (float)Math.pow(2, i / 12.0);
        }

        return tmp;
    }

    public static void play(int note, String name) {
        String fullName = instrumentAlias.get(name);
        Sound sample = resman.manager.get(fullName, Sound.class);
        sample.stop();
        sample.play(0.2f, scale[note], 0.0f);
    }
}
