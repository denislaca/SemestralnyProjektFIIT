package Models.Sklad;


import Models.Observer.Observer;
import Models.Tovar.Tovar;
import Models.Truck.HeavyTruck;
import Models.Truck.OrdinaryTruck;
import Models.Truck.Truck;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by Denis-iMac on 2.4.17.
 */
public class Dispecing {
    private ArrayList<Sklad> CentralneSklady;
    private Vector<Truck> ActiveTrucks;
    private final List<Observer> observers = new Vector<>();

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
        fromWarehouse.getTableData().remove(itemIndex);
        toWarehouse.setNaskladneny_tovar(temp);
        toWarehouse.setTableData(temp);
    }

    public void activateTrucks(String selected_warehouse) {
        if (selected_warehouse == "All") {
            for (int i = 0; i < CentralneSklady.size(); i++) {
                Sklad sklad = getSklady(i);
                while (sklad.getNaskladneny_tovarSize() > 0) {
                    ActiveTrucks.addAll(sklad.sendTrucks(new OrdinaryTruck()));
                    if(sklad.getNaskladneny_tovarSize() > 0) ActiveTrucks.addAll(sklad.sendTrucks(new HeavyTruck()));
                    if(sklad.getNaskladneny_tovarSize() > 0) ActiveTrucks.addAll(sklad.sendTrucks(new OrdinaryTruck()));
                }
            }
        } else {
            Sklad sklad = getSklady(getIntexOfSklad(selected_warehouse));
            while(sklad.getNaskladneny_tovarSize() > 0) {
                ActiveTrucks.addAll(sklad.sendTrucks(new OrdinaryTruck()));
                if(sklad.getNaskladneny_tovarSize() > 0) ActiveTrucks.addAll(sklad.sendTrucks(new HeavyTruck()));
                if(sklad.getNaskladneny_tovarSize() > 0) ActiveTrucks.addAll(sklad.sendTrucks(new OrdinaryTruck()));
            }
        }
    }

    //-------METHODS-------

    //-------OBSERVER------

    public void AddObserver(Observer observer)
    {
        observers.add(observer);
    }

    public void AllertObservers(){
            observers.get(observers.size()-1).ObserveTrucks();
    }

    //-------OBSERVER------

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

    /**
     *
     * @param index - index skladu
     * @param sklad
     */
    public void setCentralneSklady(int index, Sklad sklad){
        CentralneSklady.set(index, sklad);
    }
    //-------SETTERS-------
}
