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

/**
 * The DesignManager class handles the storage and management of color designs
 * for the calculator application. It allows saving, retrieving, and deleting
 * designs in XML format.
 */
public class DesignManager {
    private static final Map<String, Map<String, Color>> designs = loadSavedDesigns();

    /**
     * Adds a design with the specified colors for background,
     * number, operator, and equals button to the designs.
     *
     * @param name      the name of the design to save
     * @param background the color for the background
     * @param number    the color for the number buttons
     * @param operator  the color for the operator buttons
     * @param equals    the color for the equals button
     */
    public static void addCurrentDesign(String name, Color background, Color number, Color operator, Color equals) {
        Map<String, Color> design = new HashMap<>();
        design.put("background", background);
        design.put("number", number);
        design.put("operator", operator);
        design.put("equals", equals);
        designs.put(name, design);
        saveDesignsToFile();
    }

    /**
     * Saves the current designs to an XML file.
     */
    private static void saveDesignsToFile() {
        //Save in XML File
        try (FileOutputStream fos = new FileOutputStream(Config.FILENAME);
             XMLEncoder encoder = new XMLEncoder(fos)) {
            encoder.writeObject(designs);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("fail saving");
        }
    }

    /**
     * Retrieves a design by its name.
     *
     * @param name the name of the design to retrieve
     * @return a Map containing the colors for the specified design, or null if not found
     */
    public static Map<String, Color> getDesignByName(String name) {
        return loadSavedDesigns().get(name);
    }

    /**
     * Loads and returns the saved designs from the XML file.
     *
     * @return a Map containing all saved designs
     */
    private static Map<String, Map<String, Color>> loadSavedDesigns() {
        Map<String, Map<String, Color>> result;
        try (FileInputStream fis = new FileInputStream(Config.FILENAME);
             XMLDecoder decoder = new XMLDecoder(fis)) {
            result = (Map<String, Map<String, Color>>) decoder.readObject();
            return result;
        } catch (IOException | ClassCastException e) {
            System.out.println("fail");
            return new HashMap<String, Map<String, Color>>();
        }
    }

    /**
     * Deletes a design by its name. The default design cannot be deleted.
     *
     * @param name the name of the design to delete
     */
    public static void deleteDesignByName(String name) {
        if (!name.equals("Default")) {
            designs.remove(name);
            saveDesignsToFile();
        }
    }

    /**
     * Retrieves the names of all saved designs.
     *
     * @return an array of design names
     */
    public static String[] getAllDesignNames() {
        return loadSavedDesigns().keySet().toArray(new String[0]);
    }
}
