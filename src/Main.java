import javax.swing.*;
import java.time.LocalDate;
import java.util.Collections;

public class Main {
    private int frequencyOfWatering;

    public static void main(String[] args) throws Plant.PlantException {
        System.out.println("\n------Pokojové rostliny------");

        // Vytvoření seznamu rostlin
        PlantList testPlantList = loadPlants(chooseFile());

        System.out.println("\nVypsání načteného seznamu rostlin z textového souboru");

        // Přidání jedné rostliny, odstranění jedné rostliny a uložení seznamu do nového souboru
        if (testPlantList != null) {
            testPlantList.printWateringInfo();

            Plant newPlant1 = null;
            try {
                newPlant1 = new Plant("Řůže", "Krásná červená květina", LocalDate.of(2024, 11, 1), LocalDate.of(2024, 11, 1), 7);
            } catch (Plant.PlantException e) {
                System.err.println("Chyba při přidávání květiny: " + e.getMessage());
            }
            testPlantList.addPlant(newPlant1);

            testPlantList.removeItem(2);

            // Přidej 10 rostlin s popisem „Tulipán na prodej 1“ až „Tulipán na prodej 10“
            Plant newPlant2;
            for (int i = 1; i <= 10; i++) {
                try {
                    newPlant2 = new Plant("Tulipán na prodej " + i, "", LocalDate.of(2024, 10, 20), LocalDate.of(2024, 10, 23), 14);
                    testPlantList.addPlant(newPlant2);
                } catch (Plant.PlantException e) {
                    System.err.println("Chyba při přidávání květiny: " + e.getMessage());
                }
            }

            savePlants(Settings.getDefaultSaveFileName(), testPlantList);

            System.out.println("\nTest načtení nově uloženého souboru po přidání dvou rostlin a odebrání jedné rostliny:");
            PlantList newPlantList = loadPlants(Settings.getDefaultSaveFileName());
            newPlantList.printWateringInfo();

            // Seřazení listu dle názvu rostliny
            System.out.println("\nSeřazení listu dle názvu květiny:");
            Collections.sort(newPlantList.getPlantsList());
            newPlantList.printWateringInfo();

            // Seřazení listu dle data zálivky
            System.out.println("\nSeřazení listu dle data zálivky:");
            newPlantList.getPlantsList().sort(new WateringDateComparator());
            newPlantList.printWateringInfo();
        }
    }

    private static String chooseFile() {
        String[] options = {"Správný seznam", "Špatné datum", "Špatná frekvence"};

        var name = JOptionPane.showOptionDialog(null, "Vyber soubor:", "Výběr čteného souboru",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);

        return switch (name) {
            case 0 -> Settings.getDefaultFileName();
            case 1 -> Settings.getTestWrongDate();
            case 2 -> Settings.getTestWrongFrequency();
            default -> null;
        };
    }

    // Metoda pro načtení seznamu rostlin ze souboru s požadovaným jménem
    private static PlantList loadPlants(String filename) {
        PlantList plantList = null;
        try {
            plantList = PlantList.loadFromFile(filename);
        } catch (Plant.PlantException e) {
            System.err.println("Chyba při čtení souboru: " + e.getLocalizedMessage());
        }
        return plantList;
    }

    // Metoda pro uložení seznamu rostlin do souboru s požadovaným jménem
    private static void savePlants(String filename, PlantList plantListToSave) {
        try {
            PlantList.saveToFile(filename, plantListToSave);
        } catch (Plant.PlantException e) {
            System.err.println("Chyba při ukládání souboru: " + e.getLocalizedMessage());
        }
    }
    public void setFrequencyOfWatering(int frequencyOfWatering) throws PlantException {
        if (frequencyOfWatering <= 0) {
            throw new PlantException("Zadaná hodnota frekvence zálivky musí být větší než nula.");
        }
        this.frequencyOfWatering = frequencyOfWatering;
    }


    public int getFrequencyOfWatering() {
        return frequencyOfWatering;
    }
}
