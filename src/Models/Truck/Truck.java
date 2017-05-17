package Models.Truck;

import Models.Tovar.Tovar;
import Models.Tracker.Tracker;

import java.util.Vector;

import static java.lang.Math.abs;

/**
 * Created by Denis-iMac on 26.3.17.
 */
public abstract class Truck implements Tracker {
    private int kapacita;
    private int rychlost;
    private int zataz;
    private Vector<Tovar> nalozenyTovar;
    private int time;

    private String truckIDColumn = new String("");
    private String totalWeightColumn = new String("");
    private String warehouseColumn = new String("");
    private String destinationColumn = new String("");
    private String timeRemainingColumn = new String("");

    Truck() {
        nalozenyTovar = new Vector<Tovar>();
    }

    Truck(String ID, String Weight, String Warehouse, String destination, String Time) {
        setTruckIDColumn(ID);
        setTotalWeightColumn(Weight);
        setWarehouseColumn(Warehouse);
        setTimeRemainingColumn(Time);
        setDestinationColumn(destination);
    }

    //-------METHODS-------

    /**
     * Metoda ktora urcuje ci je do dodavky mozne nalozit dany tovar.
     * @param tovar
     */
    public void loadTruck(Tovar tovar) {
        if (getKapacita() > getZataz() + tovar.getHmotnost()) {
            nalozenyTovar.add(tovar);
            setZataz(getZataz() + tovar.getHmotnost());
        }
    }

    /**
     * Hash funkcia - funkcia vezme string a vytvori Hash
     * @param string
     * @return
     */

    @Override
    public int Hash(String string){
        int sum = 0;
        for(int i = 0; i < string.length(); i++) {
            sum+= Math.abs((string.charAt(i)*abs(Math.pow(263,i)%1000000007))%1000000007);
            sum%=1000000007;
        }
        return Math.abs(sum);
    }

    //-------METHODS-------


    //-------GETTERS-------


    public int getTime() {
        return time;
    }

    public String getTruckIDColumn() {
        return truckIDColumn;
    }

    public String getDestinationColumn() {
        return destinationColumn;
    }

    public String getTotalWeightColumn() {
        return totalWeightColumn;
    }

    public String getWarehouseColumn() {
        return warehouseColumn;
    }

    public String getTimeRemainingColumn() {
        return timeRemainingColumn;
    }

    public int getZataz() {
        return zataz;
    }

    public int getRychlost() {
        return rychlost;
    }

    public Vector<Tovar> getNalozenyTovar() {
        return nalozenyTovar;
    }

    public int getKapacita() {
        return kapacita;
    }

    //-------GETTERS-------

    //-------SETTERS-------


    public void setTime(int time) {
        this.time = time;
    }

    public void setDestinationColumn(String destinationColumn) {
        this.destinationColumn = destinationColumn;
    }

    public void setTimeRemainingColumn(String timeRemainingColumn) {
        this.timeRemainingColumn = timeRemainingColumn;
    }

    public void setTotalWeightColumn(String totalWeightColumn) {
        this.totalWeightColumn = totalWeightColumn;
    }

    public void setTruckIDColumn(String truckIDColumn) {
        this.truckIDColumn = truckIDColumn;
    }

    public void setWarehouseColumn(String warehouseColumn) {
        this.warehouseColumn = warehouseColumn;
    }

    public void setZataz(int zataz) {
        this.zataz = zataz;
    }

    public void setKapacita(int kapacita) {
        this.kapacita = kapacita;
    }

    public void setRychlost(int rychlost) {
        this.rychlost = rychlost;
    }

    //-------SETTERS-------
}
