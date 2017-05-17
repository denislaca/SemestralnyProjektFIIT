package Controllers;

import Models.Observer.Observer;
import Models.Observer.ObserverItem;
import Models.Observer.ObserverTruck;
import Models.Sklad.*;
import Models.Tovar.Tovar;
import Models.Truck.Truck;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Hlavny controller
 */
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
    @FXML public TextArea customerConsole;

    private Tovar tempTovar;
    private Dispecing dispecing;
    private static int hashID=0;
    private TrucksTablePopulator populator;

    class WrongFormatException extends NumberFormatException{
        public void VypisPricinu(){
            System.out.println("Cannot parse input - wrong format");
        }
    }

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
        customerConsole.clear();
        dispecing = new Dispecing();
        populator = new TrucksTablePopulator();
        System.out.println("Done Initializing FXML");
    }

    /**
     * Vypocita cenu produktu
     */
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

        } catch (WrongFormatException ex) {
            ex.VypisPricinu();
        }
        System.out.println("Done CalculatePrice");
    }

    /**
     * Aktivuje dodavky
     */

    public void ActivateTrucks(){
        System.out.println("Sending trucks");
        dispecing.activateTrucks(warehouseChoiceBox.getValue().toString());
        if(dispecing.getSklady(dispecing.getIntexOfSklad(warehouseChoiceBox.getValue().toString())).getTableData().size()==0)
            return;
        populator.clearTableData();
        for(Truck truck : dispecing.getActiveTrucks()){
            System.out.println(truck.toString());
            dispecing.AddObserver( new ObserverTruck(truck, dispecing, customerConsole));
            truck.setTotalWeightColumn(Integer.toString(truck.getZataz()));
            truck.setTruckIDColumn(Integer.toString(populator.getID()));
            populator.setID();
            truck.setDestinationColumn(truck.getNalozenyTovar().lastElement().getDodanie());
            truck.setWarehouseColumn(truck.getNalozenyTovar().lastElement().getSklad());
            truck.setTimeRemainingColumn(Integer.toString(truck.getNalozenyTovar().lastElement().getVzdialenost()%10009));
            populator.setTableData(truck);
            Observer observe = new ObserverTruck(truck,dispecing, customerConsole);
            dispecing.AddObserver(observe);
            dispecing.AllertObservers();
        }

        warehouseTableController.getItems().clear();
        trucksTableController.setItems(populator.getTableData());

        System.out.println("Done sending trucks");
    }

    /**
     * Vytvori nove okno v ktorom je mozne upravit vybrany tovar.
     */
    public void EditItem(){
        int indexTovar = warehouseTableController.getSelectionModel().getSelectedIndex();
        if(indexTovar == -1) {
            System.out.println("No item selected");
            return;
        }
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/inspect.fxml"));
            fxmlLoader.setController(new InspectController(warehouseTableController, warehouseChoiceBox, dispecing));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Inspect Item");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        System.out.println("Selected item at index " + Integer.toString(indexTovar));


    }

    /**
     * Upravi uz vyslanu dodavku
     */
    public void EditTruck(){
        int indexTovar = trucksTableController.getSelectionModel().getSelectedIndex();
        if(indexTovar == -1) {
            System.out.println("No truck selected");
            return;
        }
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/truck.fxml"));
            fxmlLoader.setController(new TruckController(trucksTableController, dispecing));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Inspect Item");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        ActivateTrucks();
        System.out.println("Selected truck at index " + Integer.toString(indexTovar));


    }

    /**
     * Vyfiltruje tovar pre vybrany sklad
     */
    public void FilterList(){
        int index = dispecing.getIntexOfSklad(warehouseChoiceBox.getValue().toString());
        Sklad sklad = dispecing.getSklady(index);
        System.out.println("Filter list for " + sklad.getNazov());
        warehouseTableController.setItems(sklad.getTableData());
        System.out.println("Finish filtering");

    }

    /**
     * Prida tovar do vybraneho skladu
     */
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
        } catch (WrongFormatException ex) {
            ex.VypisPricinu();
        }
        sklad.getTableData().get(sklad.getTableData().size()-1).setSklad(tempTovar.getSklad());
        Observer observe = new ObserverItem(sklad, customerConsole);
        sklad.AddObserver(observe);
        dispecing.setCentralneSklady(index, sklad);
        sklad.ObserveLast();
        System.out.println("Done addTovar");
        warehouseTableController.setItems(sklad.getTableData());
        System.out.println("Done AddToTable");
    }

}
