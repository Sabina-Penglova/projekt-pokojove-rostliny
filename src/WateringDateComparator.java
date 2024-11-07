
import java.util.Comparator;

public class WateringDateComparator implements Comparator<Plant> {
    @Override
    public int compare(Plant plant, Plant otherplant) {

        return plant.getWatering().compareTo(otherplant.getWatering());
    }
}