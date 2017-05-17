package Controllers;

import Models.Sklad.Dispecing;
import Models.Sklad.Sklad;
import Models.Tovar.Tovar;
import Models.Truck.Truck;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Denis-iMac on 17.5.17.
 */
public class TruckController implements Initializable{
    @FXML public TextField weightTruckInspect;
    @FXML public TextField destinationTruckInspect;
    @FXML public TextField warehouseTruckInspect;

    private TableView<Truck> truckTableController;
    private Dispecing dispecing;
    private String ID;
    private String Time;

    public TruckController(TableView<Truck> table, Dispecing dispecing){
        this.truckTableController = table;
        this.dispecing = dispecing;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Truck truck = truckTableController.getSelectionModel().getSelectedItem();
        weightTruckInspect.setText(truck.getTotalWeightColumn());
        destinationTruckInspect.setText(truck.getDestinationColumn());
        warehouseTruckInspect.setText(truck.getWarehouseColumn());
        Time = truck.getTimeRemainingColumn();
        ID = truck.getTruckIDColumn();
    }

    /**
     * Upravi vybrany produnkt na aktualne hodnoty
     */
    public void EditInspectedTruck(){
        int indexTruck = truckTableController.getSelectionModel().getSelectedIndex();
        if( indexTruck < 0)
            return;
        Truck truck = dispecing.getActiveTrucks().get(indexTruck);

        truck.setTimeRemainingColumn(Time);
        truck.setWarehouseColumn(warehouseTruckInspect.getText());
        truck.setTotalWeightColumn(weightTruckInspect.getText());
        truck.setDestinationColumn(destinationTruckInspect.getText());
        truck.setTruckIDColumn(ID);

        dispecing.getActiveTrucks().set(indexTruck,truck);
    }
}
