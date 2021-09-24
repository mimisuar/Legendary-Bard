package msuarez.monsters;

import com.badlogic.gdx.graphics.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by APCS on 11/29/2016.
 */

public class MonsterMaker {
    private HashMap<String, MonsterTemplate> m_ = new HashMap<String, MonsterTemplate>();
    private ArrayList<MonsterTemplate> monsterTemplates = new ArrayList<MonsterTemplate>();

    public void load(String fname) {
        MonsterTemplate m = new MonsterTemplate();
        MonsterTemplateLoader.load(fname, m);
        monsterTemplates.add(m);
    }

    public void sort() {
        Collections.sort(monsterTemplates, new Comparator<MonsterTemplate>() {
            @Override
            public int compare(MonsterTemplate a, MonsterTemplate b) {
                return a.exp < b.exp ? -1 : a.exp == b.exp ? 0 : 1;
            }
        });

        return;
    }

    public Monster makeMonster(int playerProgress) {
        Object[] values = monsterTemplates.toArray();
        return new Monster((MonsterTemplate) values[(playerProgress - 1) % values.length]);

    }

    public Monster makeRandomMonster() {
        Object[] values = monsterTemplates.toArray();
        int id = (int)(values.length * Math.random());
        return new Monster((MonsterTemplate)values[id]);
    }

    public Monster makeMonsterByName(String name) {
        return null;
    }
}
