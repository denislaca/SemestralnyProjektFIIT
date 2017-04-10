package Controllers;

import Models.Sklad.Dispecing;
import Models.Sklad.Sklad;
import Models.Tovar.Tovar;
import Models.Truck.Truck;
import com.sun.xml.internal.bind.v2.runtime.property.PropertyFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainFXMLController implements Initializable{
    @FXML public Button calcPriceButton;
    @FXML public Button addWaresButton;
    @FXML public CheckBox fragileCheck;
    @FXML public ChoiceBox warehouseChoice;
    @FXML public ChoiceBox warehouseChoiceButton;
    @FXML public TextField weightInput;
    @FXML public TextField deliveryDateInput;
    @FXML public TextField destinationInput;
    @FXML public TextField priceInput;
    @FXML public TableView<Tovar> warehouseTableController;
    @FXML public TableView<Truck> trucksTableController;
    @FXML public TableColumn<Tovar, String> itemIDColumn;
    @FXML public TableColumn<Tovar, String> weightColumn;
    @FXML public TableColumn<Tovar, String> itemDestinationColumn;
    @FXML public TableColumn<Tovar, String> priceColumn;
    @FXML public TableColumn<Tovar, String> dateColumn;

    private Tovar tempTovar;
    private Dispecing dispecing;
    private TableViewPopulator populator;
    private static int hashID=0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Initializing FXML");
        List<String> list = new ArrayList<String>();
        list.add("Bratislava");
        list.add("Trnava");
        list.add("Nitra");
        list.add("Zilina");
        list.add("Banska Bystrica");
        list.add("Presov");
        list.add("Kosice");

        itemIDColumn.setCellValueFactory(new PropertyValueFactory<Tovar, String>("idColumnProperty"));
        weightColumn.setCellValueFactory(new PropertyValueFactory<Tovar, String>("itemWeightColumnProperty"));
        itemDestinationColumn.setCellValueFactory(new PropertyValueFactory<Tovar, String>("itemDestinationColumnProperty"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Tovar, String>("priceColumnProperty"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Tovar, String>("dateColumnProperty"));

        ObservableList warehouseList = FXCollections.observableList(list);
        ObservableList warehouseObList = FXCollections.observableList(list);
        warehouseObList.add(0,"All");
        warehouseChoice.getItems().clear();
        warehouseChoice.setItems(warehouseList);
        warehouseChoice.setValue("Bratislava");
        warehouseChoiceButton.getItems().clear();
        warehouseChoiceButton.setItems(warehouseObList);
        warehouseChoiceButton.setValue("Bratislava");

        dispecing = new Dispecing();
        populator = new TableViewPopulator();
        System.out.println("Done Initializing FXML");
    }

    public void CalculatePrice() {
        System.out.println("Start CalculatePrice");
        tempTovar = new Tovar(
                Integer.parseInt(weightInput.getText()),
                warehouseChoice.getValue().toString(),
                destinationInput.getText(),
                Integer.parseInt(deliveryDateInput.getText()),
                fragileCheck.isSelected()
        );
        priceInput.setText(String.format("%.2f%n", tempTovar.getCena()));
        System.out.println("Done CalculatePrice");
    }

    public void filterTable(){
        warehouseTableController.getItems().clear();
        ObservableList<Tovar> obList = FXCollections.observableArrayList();
        obList.setAll(populator.filter(populator.getTableData(),warehouseChoiceButton.getValue().toString()));
        if(obList.size()>0)
            warehouseTableController.setItems(obList);
    }


    public void addTovar(){
        System.out.println("Start addTovar");
        if(tempTovar == null){
            System.out.println("Tovar not initialized");
            return;
        }

        int index = dispecing.getIntexOfSklad(tempTovar.getSklad());
        if(index == -1)
            return;

        Sklad sklad = dispecing.getSklady(index);
        sklad.setNaskladneny_tovar(tempTovar);
        dispecing.setCentralneSklady(index, sklad);
        populator.setTableData(new Tovar(
                Integer.toString(tempTovar.Hash(tempTovar.getSklad())+hashID++),
                Integer.toString(tempTovar.getHmotnost()),
                tempTovar.getDodanie(),
                String.format("%.2f%n",tempTovar.getCena()),
                Integer.toString(tempTovar.getDodaciaLehota())
        ));
        System.out.println("Done addTovar");
        warehouseTableController.setItems(populator.getTableData());
        System.out.println("Done AddToTable");
    }

}