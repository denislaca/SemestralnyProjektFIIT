package Models.Observer;

import Models.Sklad.Dispecing;
import Models.Sklad.Sklad;
import Models.Truck.Truck;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * Created by Denis-iMac on 10.5.17.
 */
public class ObserverItem extends Observer {
    private Sklad sklad;
    private TextArea console;

    /**
     * @param sklad - sklad
     * @param console - uzivatelska konzola
     */
    public ObserverItem(Sklad sklad, TextArea console){
        this.sklad = sklad;
        this.console = console;
        sklad.AddObserver(this);
    }

    @Override
    public void Observe() {
        console.appendText("Tovar ID: "+ sklad.getTableData().get(sklad.getTableData().size()-1).getIdColumnProperty() +"\n");
    }

    @Override
    public void Update() {
        console.appendText("Bol pridany tovar - ID: " + sklad.getTableData().get(sklad.getTableData().size()-1).getIdColumnProperty() +"\n");
    }

}
