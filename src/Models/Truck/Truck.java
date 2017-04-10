package Models.Truck;

import Models.Tovar.Tovar;
import Models.Tracker.Route;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

/**
 * Created by Denis-iMac on 26.3.17.
 */
public abstract class Truck implements Route {
    private int kapacita;
    private int rychlost;
    private int zataz;
    private Vector<Tovar> nalozenyTovar;
    private int time;
    private Timer timer = new Timer();

    Truck() {
        nalozenyTovar = new Vector<Tovar>();
    }

    @Override
    public void setTimer(int initialTime) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                time--;
            }
        }, (2*1000*initialTime)/getRychlost());
    }

    //-------METHODS-------
    public void loadTruck(Tovar tovar) {
        if(getKapacita() > getZataz() + tovar.getHmotnost()) {
            nalozenyTovar.add(tovar);
            setZataz(getZataz()+tovar.getHmotnost());
        }
    }
    //-------METHODS-------

    //-------GETTERS-------


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
