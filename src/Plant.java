import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Plant implements Comparable<Plant>{
    private String name;
    private String notes;
    private LocalDate planted;
    private LocalDate watering;
    private int frequencyOfWatering;

    //region Konstruktory pro vytvoření kytky dle zadání
    public Plant(String name, String notes, LocalDate planted, LocalDate watering, int frequencyOfWatering) throws PlantException {
        this.name = name;
        this.notes = notes;
        this.planted = planted;
        this.setWatering(watering);
        this.setFrequencyOfWatering(frequencyOfWatering);
    }

    public void doWateringNow() {
        this.watering = LocalDate.now();
    }


    //region Přístupové metody dle zadání s ošetřenýma podmínkama
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDate getPlanted() {
        return this.planted;
    }

    public void setPlanted(LocalDate planted) {
        this.planted = planted;
    }

    public LocalDate getWatering() {
        return this.watering;
    }

    public void setWatering(LocalDate watering) throws PlantException {
        if (watering.isBefore(this.getPlanted())) {
            throw new PlantException("Datum zálivky musí být před datem zasazení nebo v den zasazení.");
        } else {
            this.watering = watering;
        }
    }

    public int getFrequencyOfWatering() {
        return this.frequencyOfWatering;
    }

    public void setFrequencyOfWatering(int frequencyOfWatering) throws PlantException {
        if (frequencyOfWatering <= 0) {
            throw new PlantException("Zadaná hodnota frekvence zálivky musí být větší než nula.");
        } else {
            this.frequencyOfWatering = frequencyOfWatering;
        }
    }
    //endregion

    //Informace o kytce dle zadání s formátovaným výstupním datem
    public String getWateringInfo() {

        return "Název květiny: " + this.getName() +
                ", Datum poslední zálivky: " + this.getWatering().format(DateTimeFormatter.ofPattern("d.M.uuuu")) +
                ", Datum doporučené další zálivky: " + this.getWatering().plusDays((long) this.getFrequencyOfWatering()).format(DateTimeFormatter.ofPattern("d.M.uuuu"));
    }


    @Override
    public int compareTo(Plant otherPlant) {
        return this.getName().compareTo(otherPlant.getName());
    }

    public LocalDate getLastWatering() {
        return null;
    }

    public class PlantException extends Throwable {
        public PlantException(String s) {

        }
    }
}