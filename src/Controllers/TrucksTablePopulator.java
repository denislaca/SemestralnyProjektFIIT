package Controllers;

import Models.Truck.Truck;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by Denis-iMac on 11.4.17.
 */
public class TrucksTablePopulator {
    private final ObservableList<Truck> tableData = FXCollections.observableArrayList();

    public  ObservableList<Truck> getTableData() {
        return tableData;
    }

    public void setTableData(Truck... truck) {
        this.tableData.addAll(truck);
    }

}
