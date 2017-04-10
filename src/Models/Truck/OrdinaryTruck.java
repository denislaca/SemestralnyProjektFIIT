package Models.Truck;

import Models.Tovar.Tovar;
import Models.Tracker.Tracker;

/**
 * Created by Denis-iMac on 28.3.17.
 */
public class OrdinaryTruck extends Truck implements Tracker{
    public OrdinaryTruck(){
        super();
        setKapacita(500);
        setRychlost(80);
    }

    //-------METHODS-------
    @Override
    public void loadTruck(Tovar tovar) {
        if (tovar.getHmotnost()>100)
            return;
        super.loadTruck(tovar);
    }

    public int track(){
        return 0;
    }

    public void callback(){ }
    //-------METHODS-------
}
