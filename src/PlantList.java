import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlantList {
    private List<Plant> plantsList = new ArrayList();

    //region Knstruktory
    public PlantList() {
        this.plantsList.addAll(this.plantsList);
    }

    public PlantList(List<Plant> plantsList) {
        this.plantsList.addAll(plantsList);
    }

    public List<Plant> getPlantsList() {
        return this.plantsList;
    }
    //endregion

    //Metoda pro přidání kytky
    public void addPlant(Plant newPlant) {

        this.plantsList.add(newPlant);
    }

    //Metoda pro vymazání kytky s daným indexem
    public void removeItem(int indexOfPlant) {
        this.plantsList.remove(indexOfPlant);
    }

    public Plant getItem(int indexOfPlant) {
        return (Plant) this.plantsList.get(indexOfPlant);
    }

    //Metoda pro vypsání informace o všech kytkách v daném seznamu
    public void printWateringInfo() {
        for (Plant plant : getPlantsList()) {
            System.out.println(plant.getWateringInfo());
        }
    }

    //Metoda pro načtení kytek z textového souboru a jejich přidání do seznamu
    public static PlantList loadFromFile(String filename) throws PlantException {
        PlantList newPlantlist = new PlantList();
        int lineNumber = 1;
        try {
            Scanner scanner = new Scanner(new BufferedReader(new FileReader(filename)));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                parseLine(line, newPlantlist, lineNumber);
                lineNumber++;
            }

        } catch (FileNotFoundException e) {
            throw new PlantException("Soubor " + filename + " nelze otevřít");
        }
        return newPlantlist;
    }

    //Metoda procházení textového souboru a přiřazení jednotlivých parametrů k proměnným
    public static void parseLine(String line, PlantList plantList, int lineNumber) throws PlantException {
        String[] blocks = line.split("\\t");

        if (blocks.length != 5) {
            throw new PlantException("Nesprávný počet položek na řádku: " + line + "! Počet položek: " + blocks.length + ".");

        }
        String name = blocks[0].trim();
        String note = blocks[1].trim();
        int frequencyOfWatering = 0;
        try {
            frequencyOfWatering = Integer.parseInt(blocks[2].trim());
        } catch (NumberFormatException e) {
            throw new PlantException("Nesprávně zadaný formát intervalu zálivky \"" + blocks[2] + "\" na řádku " + lineNumber + ". Zadaná hodnota musí být celé číslo!");
        }

        LocalDate watering = null;
        try {
            watering = LocalDate.parse(blocks[3].trim());
        } catch (DateTimeParseException e) {
            throw new PlantException("Nesprávně zadaný formát data zálivky \"" + blocks[3] + "\" na řádku " + lineNumber + ". Zadaná hodnota musí být ve formátu YYYY-MM-DD !");
        }

        LocalDate planted = null;
        try {
            planted = LocalDate.parse(blocks[4].trim());
        } catch (DateTimeParseException e) {
            throw new PlantException("Nesprávně zadaný formát data zasazenní \"" + blocks[4] + "\" na řádku " + lineNumber + ". Zadaná hodnota musí být ve formátu YYYY-MM-DD !");
        }

        Plant newPlant = null;
        try {
            newPlant = new Plant(name, note, planted, watering, frequencyOfWatering);
        } catch (Plant.PlantException e) {
            throw new RuntimeException(e);
        }
        plantList.addPlant(newPlant);
    }
    //Metoda pro uložení seznamu kytek do textového souboru (tabulátory jsou uloženy jako konstanty třídy Settings)
    public static void saveToFile(String filename, PlantList plantlist) throws PlantException {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filename)))) {
            for (Plant plant : plantlist.plantsList) {
                writer.println(plant.getName() + Settings.getFilePlantDelimiter()
                        + plant.getNotes() + Settings.getFilePlantDelimiter()
                        + plant.getFrequencyOfWatering() + Settings.getFilePlantDelimiter()
                        + plant.getWatering() + Settings.getFilePlantDelimiter()
                        + plant.getPlanted());

            }


        } catch (IOException e) {
            throw new PlantException("Soubor \"" + filename + "\" se nepodařilo zapsat. " + e.getLocalizedMessage());
        }

    }

}