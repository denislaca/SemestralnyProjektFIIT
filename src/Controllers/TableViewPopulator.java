package Controllers;

import Models.Tovar.Tovar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by Denis-iMac on 4.4.17.
 */
public class TableViewPopulator {
    private final ObservableList<Tovar> tableData = FXCollections.observableArrayList();
    private final ObservableList<Tovar> tempObList = FXCollections.observableArrayList();

    public  ObservableList<Tovar> getTableData() {
        return tableData;
    }

    public void setTableData(Tovar tovar) {
        this.tableData.add(tovar);
    }

    public ObservableList<Tovar> filter(ObservableList<Tovar> obList, String sklad){
        tempObList.clear();
        for (int i = 0; i < tableData.size(); i++){
            if(tableData.get(i).getSklad().equals(sklad)){
                tempObList.add(tableData.get(i));
            }
        }
        return tempObList;
    }

}
