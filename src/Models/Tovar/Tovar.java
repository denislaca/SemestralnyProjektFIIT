package Models.Tovar;

import Models.Observer.Observer;
import Models.Tracker.Tracker;
import javafx.scene.control.TextField;

import java.util.Random;

import static java.lang.Math.abs;

/**
 * Created by Denis-iMac on 26.3.17.
 */
public class Tovar implements Tracker {
    private double cena;
    private int hmotnost;
    private int vzdialenost;
    private String sklad;
    private int dodaciaLehota;
    private boolean fragile;
    private String dodanie;
    private TextField console;

    public Tovar(int hmotnost, String sklad, String dodanie, int dodaciaLehota, boolean fragile) {
        setHmotnost(hmotnost);
        setSklad(sklad);
        setDodanie(dodanie);
        setDodaciaLehota(dodaciaLehota);
        setFragile(fragile);
        calcPrice();
    }

    public Tovar(String id, String hmotnost, String destinacia, String cena, String dodaciaLehota){
        setIdColumnPropery(id);
        setItemWeightColumnProperty(hmotnost);
        setItemDestinationColumnProperty(destinacia);
        setPriceColumnProperty(cena);
        setDateColumnProperty(dodaciaLehota);
    }

    //-------POPULATE-------
    private String idColumnProperty = new String("");
    private String itemWeightColumnProperty = new String("");
    private String itemDestinationColumnProperty = new String("");
    private String priceColumnProperty = new String("");
    private String dateColumnProperty = new String("");
    //-------POPULATE------

    //-------METHODS-------
    private void calcPrice() {
        Random random = new Random();
        int distance = Hash(getSklad()+getDodanie());
        setVzdialenost(distance);
        setCena(abs(((distance%263)*(double)getHmotnost())/(double)getDodaciaLehota()));
        if(isFragile())
            setCena(getCena()+2);
        System.out.println(getCena());
        observeItems();
    }

    public int Hash(String string){
        int sum = 0;
        for(int i = 0; i < string.length(); i++) {
            sum+= Math.abs((string.charAt(i)*abs(Math.pow(263,i)))%1000000007);
            sum%=1000000007;
        }
        return Math.abs(sum);
    }
    //-------METHODS-------

    //-------OBSERVE-------
    public void observeItems(){

    };

    //-------OBSERVE-------

    //-------GETTERS-------
    public double getCena() {
        return cena;
    }

    public int getHmotnost() {
        return hmotnost;
    }

    public int getVzdialenost() {
        return vzdialenost;
    }

    public String getDodanie() {
        return dodanie;
    }

    public String getSklad() {
        return sklad;
    }

    public int getDodaciaLehota() {
        return dodaciaLehota;
    }

    public boolean isFragile() {
        return fragile;
    }

    public String getIdColumnProperty(){
        return idColumnProperty;
    }

    public String getItemDestinationColumnProperty() {
        return itemDestinationColumnProperty;
    }

    public String getItemWeightColumnProperty() {
        return itemWeightColumnProperty;
    }

    public String getPriceColumnProperty() {
        return priceColumnProperty;
    }

    public String getDateColumnProperty() {
        return dateColumnProperty;
    }
    //-------GETTERS-------

    //-------SETTERS-------
    public void setVzdialenost(int vzdialenost) {
        this.vzdialenost = vzdialenost;
    }

    public void setSklad(String sklad) {
        this.sklad = sklad;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public void setDodanie(String dodanie) {
        this.dodanie = dodanie;
    }

    public void setHmotnost(int hmotnost) {
        this.hmotnost = hmotnost;
    }

    public void setDodaciaLehota(int dodaciaLehota) {
        this.dodaciaLehota = dodaciaLehota;
    }

    public void setFragile(boolean fragile) {
        this.fragile = fragile;
    }

    public void setIdColumnPropery(String id){
        this.idColumnProperty = id;
    }

    public void setItemWeightColumnProperty(String weight){
        itemWeightColumnProperty= weight;
    }

    public void setItemDestinationColumnProperty(String itemDestinationColumnProperty) {
        this.itemDestinationColumnProperty = itemDestinationColumnProperty;
    }

    public void setDateColumnProperty(String dateColumnProperty) {
        this.dateColumnProperty = dateColumnProperty;
    }

    public void setPriceColumnProperty(String priceColumnProperty) {
        this.priceColumnProperty = priceColumnProperty;
    }
    //-------SETTERS-------
}
