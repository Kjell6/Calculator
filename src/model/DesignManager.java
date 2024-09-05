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
        Map<String, Map<String, Color>> designs = loadSavedDesigns(Config.DESIGNGALLERY);
        Map<String, Color> design = new HashMap<>();
        design.put("background", background);
        design.put("number", number);
        design.put("operator", operator);
        design.put("equals", equals);
        designs.put(name, design);
        saveDesignsToFile(Config.DESIGNGALLERY, designs);
    }

    /**
     * Saves the current designs to an XML file.
     */
    private static void saveDesignsToFile(String file, Map<String, Map<String, Color>> ddesigns) {
        //Save in XML File
        try (FileOutputStream fos = new FileOutputStream(file);
             XMLEncoder encoder = new XMLEncoder(fos)) {
            encoder.writeObject(ddesigns);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("fail saving");
        }
    }

    /**
     * Deletes a design by its name. The default design cannot be deleted.
     *
     * @param name the name of the design to delete
     */
    public static void deleteDesignByName(String name) {
        Map<String, Map<String, Color>> designs = loadSavedDesigns(Config.DESIGNGALLERY);
        if (!name.equals("Default")) {
            designs.remove(name);
            saveDesignsToFile(Config.DESIGNGALLERY, designs);
        }
    }

    /**
     * Retrieves a design by its name.
     *
     * @param name the name of the design to retrieve
     * @return a Map containing the colors for the specified design, or null if not found
     */
    public static Map<String, Color> getDesignByName(String name) {
        return loadSavedDesigns(Config.DESIGNGALLERY).get(name);
    }

    /**
     * Loads and returns the saved designs from the XML file.
     *
     * @return a Map containing all saved designs
     */
    private static Map<String, Map<String, Color>> loadSavedDesigns(String file) {
        try (FileInputStream fis = new FileInputStream(file);
             XMLDecoder decoder = new XMLDecoder(fis)) {
            return (Map<String, Map<String, Color>>) decoder.readObject();
        } catch (IOException | ClassCastException e) {
            return new HashMap<>();
        }
    }

    /**
     * Retrieves the names of all saved designs.
     *
     * @return an array of design names
     */
    public static String[] getAllDesignNames() {
        return loadSavedDesigns(Config.DESIGNGALLERY).keySet().toArray(new String[0]);
    }

    /**
     * Changes the current Design
     *
     * @param designName the name of the design to apply.
     */
    public static void changeActiveDesign(String designName) {
        Map<String, Color> design = DesignManager.getDesignByName(designName);
        saveActiveDesignName(designName);
        Config.CHANGE_BACKGROUND(design.get("background"));
        Config.CHANGE_BUTTON_COLOR(design.get("number"));
        Config.CHANGE_OPERATOR_COLOR(design.get("operator"));
        Config.CHANGE_EQUAL_COLOR(design.get("equals"));
    }

    public static void saveActiveDesignName(String name) {
        //Save in XML File
        try (FileOutputStream fos = new FileOutputStream(Config.ACTIVEDESIGN);
             XMLEncoder encoder = new XMLEncoder(fos)) {
            encoder.writeObject(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getActiveDesign() {
        try (FileInputStream fis = new FileInputStream(Config.ACTIVEDESIGN);
             XMLDecoder decoder = new XMLDecoder(fis)) {
            return (String) decoder.readObject();
        } catch (IOException | ClassCastException e) {
            System.out.println("fail");
            return"Default";
        }
    }
}
