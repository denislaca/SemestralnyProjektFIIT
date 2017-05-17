package Models.Truck;

import Models.Observer.Observer;
import Models.Tovar.Tovar;
import Models.Tracker.Route;
import Models.Tracker.Tracker;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import static java.lang.Math.abs;

/**
 * Created by Denis-iMac on 26.3.17.
 */
public abstract class Truck implements Route,Tracker {
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

    @Override
    public void setTimer(int initialTime) {

    }

    //-------METHODS-------
    public void loadTruck(Tovar tovar) {
        if(getKapacita() > getZataz() + tovar.getHmotnost()) {
            nalozenyTovar.add(tovar);
            setZataz(getZataz()+tovar.getHmotnost());
        }
    }

    public int Hash(String string){
        int sum = 0;
        for(int i = 0; i < string.length(); i++) {
            sum+= Math.abs((string.charAt(i)*abs(Math.pow(263,i)%1000000007))%1000000007);
            sum%=1000000007;
        }
        return Math.abs(sum);
    }

    @Override
    public void SortBeforeRoute(int left, int right) {
        Vector<Tovar> q = new Vector<>(right+1);
        int i, j, k, mid;
        mid = (left+right) / 2;

        if (left<mid)
            SortBeforeRoute(left,mid);
        if (mid+1<right)
            SortBeforeRoute(mid+1,right);

        i=left; j=mid+1; k=left;

        while((i<=mid) && (j<=right))
        {
            if(nalozenyTovar.get(i).getVzdialenost()<=nalozenyTovar.get(j).getVzdialenost())
                q.set(k,nalozenyTovar.get(i++));
            else
                q.set(k,nalozenyTovar.get(j++));
            ++k;
        }
        while(i<=mid)
        {
            q.set(k,nalozenyTovar.get(i++));
            ++k;
        }
        while(j<=right)
        {
            q.set(k,nalozenyTovar.get(j++));
            ++k;
        }
        for(k=left; k<=right; ++k)
            nalozenyTovar.set(k, q.get(k));
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

    @Override
    public void setOnRoute(int time) {
        setTimer(time);
    }
    //-------SETTERS-------
}
