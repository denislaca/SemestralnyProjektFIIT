package Models.Truck;

import Models.Tovar.Tovar;
import Models.Tracker.Tracker;

/**
 * Created by Denis-iMac on 28.3.17.
 */
public class FastTruck extends Truck implements Tracker {

    public FastTruck(){
        super();
        setKapacita(300);
        setRychlost(100);
    }

    //-------METHODS-------
    @Override
    public void loadTruck(Tovar tovar) {
        if(tovar.isFragile() && tovar.getHmotnost()>30)
            return;
        super.loadTruck(tovar);
    }

    public int track(){
        return 0;
    }

    public void callback(){ setTime(getTime()/4);}
    //-------METHODS-------
}
