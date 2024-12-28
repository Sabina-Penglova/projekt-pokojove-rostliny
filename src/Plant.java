import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Plant implements Comparable<Plant> {
    private  String name;
    private String notes;
    private LocalDate planted;
    private LocalDate watering;
    private int frequencyOfWatering;

    // Konstruktory
    public Plant(String name, String notes, LocalDate planted, LocalDate watering, int frequencyOfWatering) throws PlantException {
        if (frequencyOfWatering <= 0) {
            throw new PlantException("Frekvence zálivky musí být větší než 0.");
        }
        if (watering.isBefore(planted)) {
            throw new PlantException("Datum zálivky nemůže být před datem zasazení.");
        }
        this.name = name;
        this.notes = notes;
        this.planted = planted;
        this.watering = watering;
        this.frequencyOfWatering = frequencyOfWatering;
    }

    // Přístupové metody
    public String getName() {
        return name;
    }

    public String getNotes() {
        return notes;
    }

    public LocalDate getPlanted() {
        return planted;
    }

    public LocalDate getWatering() {
        return watering;
    }

    public int getFrequencyOfWatering() {
        return frequencyOfWatering;
    }

    public void setFrequencyOfWatering(int frequencyOfWatering) throws PlantException {
        if (frequencyOfWatering <= 0) {
            throw new PlantException("Zadaná hodnota frekvence zálivky musí být větší než nula.");
        }
        this.frequencyOfWatering = frequencyOfWatering;
    }

    // Metody
    public String getWateringInfo() {
        return "Název: " + name +
                ", Poslední zálivka: " + watering.format(DateTimeFormatter.ofPattern("d.M.uuuu")) +
                ", Další zálivka: " + watering.plusDays(frequencyOfWatering).format(DateTimeFormatter.ofPattern("d.M.uuuu"));
    }

    public void doWateringNow() {
        this.watering = LocalDate.now();
    }

    // Výjimka
    public static class PlantException extends Exception {
        public PlantException(String message) {
            super(message);
        }
    }

    @Override
    public int compareTo(Plant other) {
        return this.name.compareTo(other.name);
    }
}

