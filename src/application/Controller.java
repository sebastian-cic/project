package application;

import java.net.URL;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;

import com.sun.org.apache.bcel.internal.generic.NEW;

import database.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import stock.Stock;

public class Controller {

		
	@FXML private TableView<Stock> tableView;
	@FXML private TableColumn<Stock, String> Symbol;
	@FXML private TableColumn<Stock, String> Date;
	@FXML private TableColumn<Stock, String> Open;
	@FXML private TableColumn<Stock, String> High;
	@FXML private TableColumn<Stock, String> Low;
	@FXML private TableColumn<Stock, String> Close;
	@FXML private TableColumn<Stock, String> Volume;
	@FXML private TableColumn<Stock, String> c2;
	@FXML private ComboBox<String> comboBox;
	
	@FXML
	public void initialize() {
		Symbol.setCellValueFactory(new PropertyValueFactory<Stock, String>("symbol"));
	    Date.setCellValueFactory(new PropertyValueFactory<Stock, String>("date"));
	    Open.setCellValueFactory(new PropertyValueFactory<Stock, String>("open"));
	    High.setCellValueFactory(new PropertyValueFactory<Stock, String>("high"));
	    Low.setCellValueFactory(new PropertyValueFactory<Stock, String>("low"));
	    Close.setCellValueFactory(new PropertyValueFactory<Stock, String>("close"));
	    Volume.setCellValueFactory(new PropertyValueFactory<Stock, String>("volume"));
	    c2.setCellValueFactory(new PropertyValueFactory<Stock, String>("c2"));
	    tableView.getItems().setAll(new DatabaseCalls().getTable(new DatabaseCalls().getNewestDate()));
	    comboBox.setItems(new DatabaseCalls().getAllDates()); 
	    comboBox.setValue("Date");
	    }
	   	
	    public void loadCSVToDataBase(){
			System.out.println("loading");
		ReadWriteData rw = new ReadWriteData();
		rw.readInCSVFiles();
	}
}
