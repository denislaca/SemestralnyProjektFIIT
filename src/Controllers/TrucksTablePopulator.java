package Controllers;

import Models.Truck.Truck;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by Denis-iMac on 11.4.17.
 */
public class TrucksTablePopulator {
    private int ID = 65748311;

    private final ObservableList<Truck> tableData = FXCollections.observableArrayList();

    public  ObservableList<Truck> getTableData() {
        return tableData;
    }

    public void setTableData(Truck... truck) {
        this.tableData.addAll(truck);
    }

    public void clearTableData(){
        tableData.remove(0,tableData.size()-1);
    }

    public void setID() {
        this.ID++;
    }

    public int getID() {
        return ID;
    }
}
