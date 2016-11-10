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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import stock.Stock;

public class Controller {

		Stock stock = new Stock("a", "b", "c", "d", "e", "f", "g", "h");
	    @FXML private TableView<Stock> tableView;
	    @FXML private TableColumn<Stock, String> Symbol;
	    @FXML private TableColumn<Stock, String> Date;
	    @FXML private TableColumn<Stock, String> Open;
	    @FXML private TableColumn<Stock, String> High;
	    @FXML private TableColumn<Stock, String> Low;
	    @FXML private TableColumn<Stock, String> Close;
	    @FXML private TableColumn<Stock, String> Volume;
	    @FXML private TableColumn<Stock, String> c2;

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
	        tableView.getItems().setAll(new ReadWriteData().getTable());
	        
	    }
	    private ObservableList<Stock> parseUserList2(){
		       ObservableList<Stock> observableList =  FXCollections.observableArrayList();
		       observableList.add(stock);
			return observableList;
		    }
	    
	    private ObservableList<Stock> parseUserList(){
	       ObservableList<Stock> observableList =  FXCollections.observableArrayList();
	       observableList.add(stock);
		return observableList;
	    }
		
	public void loadCSVToDataBase(){
		System.out.println("loading");
		ReadWriteData rw = new ReadWriteData();
		rw.readInCSVFiles();
	 }
}
