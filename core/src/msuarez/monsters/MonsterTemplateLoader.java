package msuarez.monsters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.files.FileHandleStream;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by APCS on 11/29/2016.
 */

public class MonsterTemplateLoader {
    public static void load(String fname, MonsterTemplate m) {
        Element root = initTemplateData(fname);
        readTemplateDataStats(m, root);
        readTemplateDataRange(m, root);
        readTemplateDataGfx(m, root);
    }

    private static Element initTemplateData(String fname) {

        FileHandle i = null;
        i = Gdx.files.internal(fname);

        XmlReader r = new XmlReader();
        Element root = null;
        try {
            root = r.parse(i);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return root;
    }

    private static void readTemplateDataStats(MonsterTemplate m, Element root) {
        m.name = root.getAttribute("name", "noname");

        Element hpNode = root.getChildByName("hp");
        if (hpNode != null) {
            m.hp = hpNode.getIntAttribute("val", -1);
        }

        Element shieldNode = root.getChildByName("shield");
        if (shieldNode != null) {
            m.shield = shieldNode.getIntAttribute("val", -1);
        }

        Element expNode = root.getChildByName("exp");
        if (expNode != null) {
            m.exp = expNode.getIntAttribute("val", -1);
        }

        Element attackNode = root.getChildByName("attack");
        if (attackNode != null) {
            m.attack = attackNode.getIntAttribute("val", -1);
        }

        if (m.hp <= 0) {
            System.out.println("No val attribute found in hp node, defaulting to 1.");
            m.hp = 1;
        }

        if (m.shield <= 0) {
            System.out.println("No val attribute found in shield node, defaulting to 1.");
            m.shield = 1;
        }

        if (m.exp <= 0) {
            System.out.println("No val attribute found in exp node, defaulting to 1.");
            m.exp = 1;
        }

        if (m.attack <= 0) {
            System.out.println("No val attribute found in attack node, defaulting to 1.");
        }
    }

    public static void readTemplateDataRange(MonsterTemplate m, Element root) {
        Element rangeNode = root.getChildByName("shield-range");

        m.shieldRange = new ArrayList<Integer>();
        if (rangeNode == null) {
            System.out.println("Monsters must have some sort of range!");
            return;
        }

        com.badlogic.gdx.utils.Array<Element> range = rangeNode.getChildrenByName("note");
        for (Element note : range) {
            int noteValue = note.getIntAttribute("val", -1);
            noteValue %= 12;
            m.shieldRange.add(noteValue);
        }
    }

    private static void readTemplateDataGfx(MonsterTemplate m, Element root) {
        Element gfxNode = root.getChildByName("gfx");
        m.img = gfxNode.getAttribute("src", "");

        if (m.img.equals("")) {
            System.out.println("No image will be displayed for this monster!");
        }
    }
}
