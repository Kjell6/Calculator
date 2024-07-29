package model;

import config.Config;

import java.awt.Color;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DesignManager {
    private static Map<String, Map<String, Color>> designs = getPermDesigns();

    public static void saveTempDesign(String name, Color background, Color number, Color operator, Color equals) {
        Map<String, Color> design = new HashMap<>();
        design.put("background", background);
        design.put("number", number);
        design.put("operator", operator);
        design.put("equals", equals);
        designs.put(name, design);
        savePermDesign();
    }

    private static void savePermDesign() {
        //Save in XML File
        try (FileOutputStream fos = new FileOutputStream(Config.FILENAME);
             XMLEncoder encoder = new XMLEncoder(fos)) {
            encoder.writeObject(designs);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("fail saving");
        }
    }

    public static Map<String, Color> getDesign(String name) {
        return getPermDesigns().get(name);
    }

    private static Map<String, Map<String, Color>> getPermDesigns() {
        Map<String, Map<String, Color>> result;
        try (FileInputStream fis = new FileInputStream(Config.FILENAME);
             XMLDecoder decoder = new XMLDecoder(fis)) {
            result = (Map<String, Map<String, Color>>) decoder.readObject();
            return result;
        } catch (IOException | ClassCastException e) {
            System.out.println("fail");
            return null;
        }
    }

    public static void deleteDesign(String name) {
        if (!name.equals("Default")) {
            designs.remove(name);
            savePermDesign();
        }
    }

    public static String[] getDesignNames() {
        return getPermDesigns().keySet().toArray(new String[0]);
    }
}
