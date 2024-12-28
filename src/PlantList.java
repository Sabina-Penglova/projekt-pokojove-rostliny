import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlantList {
    private final List<Plant> plantsList = new ArrayList<>();

    public List<Plant> getPlantsList() {
        return new ArrayList<>(plantsList); // Kopie seznamu
    }

    public List<Plant> getPlantsCopy() {
        return new ArrayList<>(plantsList);
    }

    public void addPlant(Plant plant) {
        plantsList.add(plant);
    }

    public Plant getPlant(int index) throws Plant.PlantException {
        if (index < 0 || index >= plantsList.size()) {
            throw new Plant.PlantException("Neplatný index: " + index);
        }
        return plantsList.get(index);
    }

    public void removeItem(int index) throws Plant.PlantException {
        if (index < 0 || index >= plantsList.size()) {
            throw new Plant.PlantException("Neplatný index: " + index);
        }
        plantsList.remove(index);
    }

    public List<Plant> getPlantsToWater() {
        List<Plant> plantsToWater = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (Plant plant : plantsList) {
            if (plant.getWatering().plusDays(plant.getFrequencyOfWatering()).isBefore(today)) {
                plantsToWater.add(plant);
            }
        }
        return plantsToWater;
    }

    public void sortByName() {
        Collections.sort(plantsList);
    }

    public void sortByWateringDate() {
        plantsList.sort(new WateringDateComparator());
    }

    public static PlantList loadFromFile(String filename) throws Plant.PlantException {
        PlantList plantList = new PlantList();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                parseLine(line, plantList);
            }
        } catch (IOException e) {
            throw new Plant.PlantException("Chyba při načítání souboru: " + e.getMessage());
        }
        return plantList;
    }

    public static void saveToFile(String filename, PlantList plantList) throws Plant.PlantException {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filename)))) {
            for (Plant plant : plantList.plantsList) {
                writer.println(plant.getName() + "\t" +
                        plant.getNotes() + "\t" +
                        plant.getFrequencyOfWatering() + "\t" +
                        plant.getWatering() + "\t" +
                        plant.getPlanted());
            }
        } catch (IOException e) {
            throw new Plant.PlantException("Chyba při ukládání souboru: " + e.getMessage());
        }
    }

    private static void parseLine(String line, PlantList plantList) throws Plant.PlantException {
        String[] parts = line.split("\\t");
        if (parts.length != 5) {
            throw new Plant.PlantException("Nesprávný formát řádku: " + line);
        }

        String name = parts[0].trim();
        String notes = parts[1].trim();
        int frequencyOfWatering;
        LocalDate watering, planted;

        try {
            frequencyOfWatering = Integer.parseInt(parts[2].trim());
            watering = LocalDate.parse(parts[3].trim());
            planted = LocalDate.parse(parts[4].trim());
        } catch (NumberFormatException | DateTimeParseException e) {
            throw new Plant.PlantException("Chyba ve formátu dat: " + e.getMessage());
        }

        Plant plant = new Plant(name, notes, planted, watering, frequencyOfWatering);
        plantList.addPlant(plant);
    }

    public void printWateringInfo() {
        for (Plant plant : plantsList) {
            System.out.println(plant.getWateringInfo());
        }
    }
}
