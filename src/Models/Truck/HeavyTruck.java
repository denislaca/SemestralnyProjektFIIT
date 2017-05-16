package Models.Truck;

import Models.Tovar.Tovar;
import Models.Tracker.Tracker;

/**
 * Created by Denis-iMac on 26.3.17.
 */
public class HeavyTruck extends Truck implements Tracker {

    public HeavyTruck(){
        super();
        setKapacita(800);
        setRychlost(60);
    }

    //-------METHODS-------
    @Override
    public void loadTruck(Tovar tovar) {
        if (tovar.getHmotnost() < 50 && tovar.isFragile())
            return;
        super.loadTruck(tovar);
    }

    public int track(){
        return 0;
    }

    public void callback(){ setTime(getTime()/2);}
    //-------METHODS-------
}
