package Controllers;

import Models.Observer.ObserverClass;
import Models.Sklad.*;
import Models.Tovar.Tovar;
import Models.Truck.Truck;
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
    @FXML public TextField weightInput;
    @FXML public TextField deliveryDateInput;
    @FXML public TextField destinationInput;
    @FXML public TextField priceInput;
    @FXML public TableView<Tovar> warehouseTableController;
    @FXML public TableView<Truck> trucksTableController;
    @FXML public TableColumn<Tovar, String> warehouseItemIDColumn;
    @FXML public TableColumn<Tovar, String> warehouseWeightColumn;
    @FXML public TableColumn<Tovar, String> warehouseItemDestinationColumn;
    @FXML public TableColumn<Tovar, String> warehousePriceColumn;
    @FXML public TableColumn<Tovar, String> warehouseDateColumn;
    @FXML public TableColumn<Truck, String> truckIDColumn;
    @FXML public TableColumn<Truck, String> totalWeightColumn;
    @FXML public TableColumn<Truck, String> originColumn;
    @FXML public TableColumn<Truck, String> timeRemainingColumn;
    @FXML public TableColumn<Truck, String> truckDestinationColumn;
    @FXML public ComboBox warehouseChoiceBox;
    @FXML public TextField customerConsole;

    private Tovar tempTovar;
    private Dispecing dispecing;
    private static int hashID=0;
    private TrucksTablePopulator populator;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Initializing FXML");
        List<String> list = new ArrayList<>();
        list.add("Bratislava");
        list.add("Trnava");
        list.add("Nitra");
        list.add("Zilina");
        list.add("Banska Bystrica");
        list.add("Presov");
        list.add("Kosice");

        // - Initialize warehouse table  - set cell value factors
        warehouseItemIDColumn.setCellValueFactory(new PropertyValueFactory<Tovar, String>("idColumnProperty"));
        warehouseWeightColumn.setCellValueFactory(new PropertyValueFactory<Tovar, String>("itemWeightColumnProperty"));
        warehouseItemDestinationColumn.setCellValueFactory(new PropertyValueFactory<Tovar, String>("itemDestinationColumnProperty"));
        warehousePriceColumn.setCellValueFactory(new PropertyValueFactory<Tovar, String>("priceColumnProperty"));
        warehouseDateColumn.setCellValueFactory(new PropertyValueFactory<Tovar, String>("dateColumnProperty"));

        // - Initialize trucks table  - set cell value factors
        truckIDColumn.setCellValueFactory(new PropertyValueFactory<Truck, String>("truckIDColumn"));
        totalWeightColumn.setCellValueFactory(new PropertyValueFactory<Truck, String>("totalWeightColumn"));
        originColumn.setCellValueFactory(new PropertyValueFactory<Truck, String>("warehouseColumn"));
        timeRemainingColumn.setCellValueFactory(new PropertyValueFactory<Truck, String>("timeRemainingColumn"));
        truckDestinationColumn.setCellValueFactory(new PropertyValueFactory<Truck, String>("destinationColumn"));

        ObservableList warehouseList = FXCollections.observableList(list);

        warehouseChoice.getItems().clear();
        warehouseChoice.setItems(warehouseList);
        warehouseChoice.setValue("Bratislava");
        warehouseChoiceBox.getItems().clear();
        warehouseChoiceBox.setItems(warehouseList);
        warehouseChoiceBox.setValue("Bratislava");

        dispecing = new Dispecing();
        populator = new TrucksTablePopulator();
        System.out.println("Done Initializing FXML");
    }

    public void CalculatePrice() {
        System.out.println("Start CalculatePrice");
        try {
            tempTovar = new Tovar(
                    Integer.parseInt(weightInput.getText()),
                    warehouseChoice.getValue().toString(),
                    destinationInput.getText(),
                    Integer.parseInt(deliveryDateInput.getText()),
                    fragileCheck.isSelected()
            );

            priceInput.setText(String.format("%.2f%n", tempTovar.getCena()));

        } catch (NumberFormatException ex) {
            System.out.print("NumberFormatExeption - ");
            System.out.println(ex.getMessage());
        }
        System.out.println("Done CalculatePrice");
    }

    /*private int kapacita;
    private int rychlost;
    private int zataz;
    private Vector<Tovar> nalozenyTovar;
    private int time;
    private Timer timer = new Timer();
    */

    public void ActivateTrucks(){
        warehouseTableController.getItems().clear();
        dispecing.activateTrucks(warehouseChoiceBox.getValue().toString());
        for(Truck truck : dispecing.getActiveTrucks()){
            dispecing.AddObserver( new ObserverClass(truck, dispecing, customerConsole));
            truck.setTotalWeightColumn(Integer.toString(truck.getZataz()));
           // truck.setTruckIDColumn(Integer.toString());
        }


    }

    public void EditItem(){
        TablePosition selected = warehouseTableController.getFocusModel().getFocusedCell();
        warehouseTableController.edit(selected.getRow(),selected.getTableColumn());
            //REmodel this line
        //Tovar tovar = selected.getTableColumn().getCellData(selected.getColumn());

    }

    public void FilterList(){
        int index = dispecing.getIntexOfSklad(warehouseChoiceBox.getValue().toString());
        Sklad sklad = dispecing.getSklady(index);
        System.out.println("Filter list for " + sklad.getNazov());
        warehouseTableController.setItems(sklad.getTableData());
        System.out.println("Finish filtering");

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
        try {
            sklad.setTableData(new Tovar(
                    Integer.toString(tempTovar.Hash(tempTovar.getSklad())+hashID++),
                    Integer.toString(tempTovar.getHmotnost()),
                    tempTovar.getDodanie(),
                    String.format("%.2f%n",tempTovar.getCena()),
                    Integer.toString(tempTovar.getDodaciaLehota())
            ));
        } catch (NumberFormatException ex) {
            System.out.print("NumberFormatExeption - ");
            System.out.println(ex.getMessage());
        }
        ObserverClass observe = new ObserverClass(sklad, customerConsole);
        sklad.AddObserver(observe);
        dispecing.setCentralneSklady(index, sklad);
        sklad.ObserveLast();
        System.out.println("Done addTovar");
        warehouseTableController.setItems(sklad.getTableData());
        System.out.println("Done AddToTable");
    }

}
