package Models.Observer;

import Models.Sklad.Dispecing;
import Models.Sklad.Sklad;
import Models.Truck.Truck;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * Created by Denis-iMac on 10.5.17.
 */
public class ObserverClass extends Observer {
    private Sklad sklad;
    private Truck truck;
    private TextArea console;

    public ObserverClass(Sklad sklad, TextArea console){
        this.sklad = sklad;
        this.console = console;
        sklad.AddObserver(this);
    }

    /**
     * @param truck - vozidlo
     * @param dispecing - dispecing
     * @param console - textField pre observer
     */
    public ObserverClass(Truck truck, Dispecing dispecing, TextArea console){
        this.truck = truck;
        this.console = console;
        dispecing.AddObserver(this);
    }

    @Override
    public void ObserveItems() {
        console.appendText("Tovar ID: "+ sklad.getTableData().get(sklad.getTableData().size()-1).getIdColumnProperty() +"\n");
    }

    @Override
    public void Update() {
        console.appendText("Bol pridany tovar - ID: " + sklad.getTableData().get(sklad.getTableData().size()-1).getIdColumnProperty() +"\n");
    }

    @Override
    public void ObserveTrucks() {
        console.appendText("Bola vyslana dodavka - ID: "+truck.getTruckIDColumn()+" Cas do prichodu: "+truck.getTime()+"\n");
    }
}
