package Models.Observer;

import Models.Sklad.Dispecing;
import Models.Truck.Truck;
import javafx.scene.control.TextArea;

/**
 * Created by Denis-iMac on 17.5.17.
 */
public class ObserverTruck extends Observer{
        private Truck truck;
        private TextArea console;

        /**
         * @param truck - vozidlo
         * @param dispecing - dispecing
         * @param console - textField pre observer
         */
        public ObserverTruck(Truck truck, Dispecing dispecing, TextArea console){
            this.truck = truck;
            this.console = console;
            dispecing.AddObserver(this);
        }

    @Override
    public void Update() {
        console.appendText("Update\n");
    }

    @Override
        public void Observe() {
            console.appendText("Bola vyslana dodavka - ID: "+truck.getTruckIDColumn()+" Cas do prichodu: "+truck.getTime()+"\n");
        }
}
