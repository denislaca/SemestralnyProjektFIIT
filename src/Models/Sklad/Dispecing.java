package Models.Sklad;

import Controllers.TrucksTablePopulator;
import Models.Sklad.Sklad;
import Models.Tovar.Tovar;
import Models.Truck.HeavyTruck;
import Models.Truck.OrdinaryTruck;
import Models.Truck.Truck;
import com.intellij.ui.speedSearch.ElementFilter;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Denis-iMac on 2.4.17.
 */
public class Dispecing {
    private ArrayList<Sklad> CentralneSklady;
    private Vector<Truck> ActiveTrucks;

    public Dispecing(){
        CentralneSklady = new ArrayList<>(0);
        ActiveTrucks = new Vector<>(0);
        addCentralneSklady("Bratislava");
        addCentralneSklady("Banska Bystica");
        addCentralneSklady("Kosice");
        addCentralneSklady("Trnava");
        addCentralneSklady("Nitra");
        addCentralneSklady("Zilina");
        addCentralneSklady("Banska Bystrica");
        addCentralneSklady("Presov");
        addCentralneSklady("Kosice");
    }

    //-------METHODS-------
    public void moveItem(Sklad fromWarehouse, Sklad toWarehouse, int itemIndex){
        Tovar temp = fromWarehouse.getNaskladneny_tovar().get(itemIndex);
        fromWarehouse.getNaskladneny_tovar().remove(itemIndex);
        toWarehouse.setNaskladneny_tovar(temp);
    }
        //NEEDS WORK
    public void activateTrucks() {
        for(int i = 0; i<CentralneSklady.size(); i++) {
            Sklad sklad = getSklady(i);
            while(sklad.getNaskladneny_tovarSize()>0) {
                ActiveTrucks.addAll(sklad.sendTrucks(new OrdinaryTruck()));
                ActiveTrucks.addAll(sklad.sendTrucks(new HeavyTruck()));
                ActiveTrucks.addAll(sklad.sendTrucks(new OrdinaryTruck()));
            }
        }
    }

    public ObservableList<Truck> parseForView(Vector<Truck> trucks){
        TrucksTablePopulator populator = new TrucksTablePopulator();
        for(Truck truck : trucks){
            populator.setTableData(truck);
        }
        return populator.getTableData();
    }
    //-------METHODS-------

    //-------GETTERS-------
    public int getIntexOfSklad(String mesto) {
        for(int i = 0; i < 9; i++){
            if(CentralneSklady.get(i).getNazov().equals(mesto))
                return i;
        }
        return -1;
    }

    public Sklad getSklady(int index){
        return CentralneSklady.get(index);
    }

    public Vector<Truck> getActiveTrucks() {
        return ActiveTrucks;
    }

    //-------GETTERS-------

    //-------SETTERS-------
    private void addCentralneSklady(String mesto) {
        CentralneSklady.add(new Sklad(mesto));
    }

    public void setCentralneSklady(int index, Sklad sklad){
        CentralneSklady.set(index, sklad);
    }
    //-------SETTERS-------
}
