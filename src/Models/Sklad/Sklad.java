package Models.Sklad;

import Models.Observer.Observer;
import Models.Tovar.Tovar;
import Models.Truck.Truck;
import Models.Truck.OrdinaryTruck;
import Models.Truck.HeavyTruck;
import Models.Truck.FastTruck;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by Denis-iMac on 26.3.17.
 */
public class Sklad {
    private String nazov;
    private Vector<Tovar> naskladneny_tovar;
    private Vector<Truck> vyslane_dodavky;
    private final List<Observer> observers = new ArrayList<>(0);
    private final ObservableList<Tovar> tableData = FXCollections.observableArrayList();

    Sklad(){
        setNazov("");
        this.naskladneny_tovar = new Vector<>(0);
        this.vyslane_dodavky = new Vector<>(0);
    }

    public Sklad(String nazov) {
        setNazov(nazov);
        this.naskladneny_tovar = new Vector<>(0);
        this.vyslane_dodavky = new Vector<>(0);
    }
    //-------METHODS-------

        //NEEDS WORK
    public Vector<Truck> sendTrucks(Truck truck){
        for(int i = 0; i<naskladneny_tovar.size(); i++)
            truck.loadTruck(getNaskladneny_tovar().get(i));
        vyslane_dodavky.add(truck);
        return vyslane_dodavky;
    }

    public void Vypis_Tovar(){
        for (int i = 0; i < tableData.size(); i++){
            System.out.println(tableData.get(i).getItemDestinationColumnProperty());
        }
    }

    //-------METHODS-------

    //-------OBSERVER------

    public void AddObserver(Observer observer)
    {
        observers.add(observer);
    }

    public void AllertObservers(){
        for (Observer observer : observers) {
            observer.ObserveItems();
        }
    }

    public void ObserveLast(){
        observers.get(observers.size()-1).Update();
    }
    //-------OBSERVER------

    //-------GETTERS-------
    public String getNazov() {
        return nazov;
    }

    public Vector<Tovar> getNaskladneny_tovar() {
        return this.naskladneny_tovar;
    }

    public Truck getDodavkaAtIndex(int index){
        return vyslane_dodavky.get(index);
    }

    public int getNaskladneny_tovarSize() { return naskladneny_tovar.size(); }

    public  ObservableList<Tovar> getTableData() {
        return tableData;
    }
    //-------GETTERS-------

    //-------SETTERS-------
    public void setTableData(Tovar tovar) {
        this.tableData.add(tovar);
    }

    public void setNaskladneny_tovar(Tovar newTovar) {
        this.naskladneny_tovar.add(newTovar);
    }

    public void setVyslane_dodavky(Truck newTruck) {
        this.vyslane_dodavky.add(newTruck);
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    public void removeDodavka(int index) {
        this.vyslane_dodavky.remove(index);
    }
    //-------SETTERS-------
}
