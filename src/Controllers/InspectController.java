package Controllers;

import Models.Sklad.Dispecing;
import Models.Sklad.Sklad;
import Models.Tovar.Tovar;
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
public class InspectController implements Initializable{
    @FXML public TextField weightInspect;
    @FXML public TextField destinationInspect;
    @FXML public TextField priceInspect;
    @FXML public TextField dateInspect;
    @FXML public TextField warehouseInspect;

    private TableView<Tovar> warehouseTableController;
    private ComboBox warehouseChoiceBox;
    private Dispecing dispecing;

    private String ID;

    public InspectController(TableView<Tovar> table, ComboBox box, Dispecing dispecing){
        this.warehouseTableController = table;
        this.warehouseChoiceBox = box;
        this.dispecing = dispecing;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Tovar tovar = warehouseTableController.getSelectionModel().getSelectedItem();
        weightInspect.setText(tovar.getItemWeightColumnProperty());
        destinationInspect.setText(tovar.getItemDestinationColumnProperty());
        priceInspect.setText(tovar.getPriceColumnProperty());
        dateInspect.setText(tovar.getDateColumnProperty());
        warehouseInspect.setText(tovar.getSklad());
        ID = tovar.getIdColumnProperty();
    }

    /**
     * Upravi vybrany produnkt na aktualne hodnoty
     */
    public void EditInspectedItem(){
        int indexTovar = warehouseTableController.getSelectionModel().getSelectedIndex();
        int index = dispecing.getIntexOfSklad(warehouseChoiceBox.getValue().toString());

        if(index < 0 || indexTovar < 0)
            return;
        Sklad sklad = dispecing.getSklady(index);
        Tovar tovar = sklad.getNaskladneny_tovar().get(indexTovar);

        if(tovar.getSklad().compareTo(warehouseInspect.getText()) != 0) {
            dispecing.moveItem(
                    dispecing.getSklady(dispecing.getIntexOfSklad(tovar.getSklad())),
                    dispecing.getSklady(dispecing.getIntexOfSklad(warehouseInspect.getText())),
                    indexTovar
            );
            sklad = dispecing.getSklady(dispecing.getIntexOfSklad(warehouseInspect.getText()));
        }

        tovar.setItemWeightColumnProperty(weightInspect.getText());
        tovar.setItemDestinationColumnProperty(destinationInspect.getText());
        tovar.setPriceColumnProperty(priceInspect.getText());
        tovar.setDateColumnProperty(dateInspect.getText());
        tovar.setIdColumnPropery(ID);
        tovar.setSklad(warehouseInspect.getText());

        sklad.setNaskladnenyTovarAtIndex(indexTovar,tovar);
        sklad.setTableDataAtIndex(indexTovar,tovar);
    }
}
