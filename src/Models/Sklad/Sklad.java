package Models.Sklad;

import Models.Tovar.Tovar;
import Models.Truck.Truck;
import Models.Truck.OrdinaryTruck;
import Models.Truck.HeavyTruck;
import Models.Truck.FastTruck;

import java.util.Vector;

/**
 * Created by Denis-iMac on 26.3.17.
 */
public class Sklad {
    private String nazov;
    private Vector<Tovar> naskladneny_tovar;
    private Vector<Truck> vyslane_dodavky;

    Sklad(){
        setNazov("");
        this.naskladneny_tovar = new Vector<Tovar>(0);
        this.vyslane_dodavky = new Vector<>(0);
    }

    public Sklad(String nazov) {
        setNazov(nazov);
        this.naskladneny_tovar = new Vector<Tovar>(0);
        this.vyslane_dodavky = new Vector<>(0);
    }
    //-------METHODS-------
        //NEEDS WORK
    public void sendTrucks(Truck truck){
        for(int i = 0; i<naskladneny_tovar.size(); i++)
            truck.loadTruck(getNaskladneny_tovar().get(i));
        vyslane_dodavky.add(truck);
    }
    //-------METHODS-------

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
    //-------GETTERS-------

    //-------SETTERS-------
    public void setNaskladneny_tovar(Tovar newTovar) {
        this.naskladneny_tovar.add(newTovar);
    }

    public void setVyslane_dodavky(Truck newTruck) {
        this.vyslane_dodavky.add(newTruck);
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }
    //-------SETTERS-------

    public void removeDodavka(int index) {
        this.vyslane_dodavky.remove(index);
    }
}
