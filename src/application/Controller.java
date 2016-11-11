package application;

import database.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import stock.Stock;

public class Controller
{

	@FXML
	private TableView<Stock> tableView;
	@FXML
	private TableColumn<Stock, String> Symbol;
	@FXML
	private TableColumn<Stock, String> Date;
	@FXML
	private TableColumn<Stock, String> Open;
	@FXML
	private TableColumn<Stock, String> High;
	@FXML
	private TableColumn<Stock, String> Low;
	@FXML
	private TableColumn<Stock, String> Close;
	@FXML
	private TableColumn<Stock, String> Volume;
	@FXML
	private TableColumn<Stock, String> c2;
	@FXML
	private ComboBox<String> comboBox;
	@FXML
	private RadioButton amexRadioButton;
	@FXML
	private RadioButton nyseRadioButton;
	@FXML
	private RadioButton nasdaqRadioButton;
	@FXML
	private RadioButton forexRadioButton;

	@FXML
	public void initialize()
	{
		//load amex data to table for most recent data
		Symbol.setCellValueFactory(new PropertyValueFactory<Stock, String>("symbol"));
		Date.setCellValueFactory(new PropertyValueFactory<Stock, String>("date"));
		Open.setCellValueFactory(new PropertyValueFactory<Stock, String>("open"));
		High.setCellValueFactory(new PropertyValueFactory<Stock, String>("high"));
		Low.setCellValueFactory(new PropertyValueFactory<Stock, String>("low"));
		Close.setCellValueFactory(new PropertyValueFactory<Stock, String>("close"));
		Volume.setCellValueFactory(new PropertyValueFactory<Stock, String>("volume"));
		c2.setCellValueFactory(new PropertyValueFactory<Stock, String>("c2"));
		tableView.getItems().setAll(new DatabaseCalls().getTable(new DatabaseCalls().getNewestDate()));
		//get all available dates for stock data into combo box
		comboBox.setItems(new DatabaseCalls().getAllDates());
		comboBox.setValue("Date");
	}

	/**
	 * load stock data from CSV files to database.File -> load CSV to database in menu bar
	 */
	public void loadCSVToDataBase()
	{
		System.out.println("loading");
		ReadWriteData rw = new ReadWriteData();
		rw.readInCSVFiles();
		System.out.println("Finished loading data");
	}
	/**
	 * Load data to table by date from combo box and exchange radio button
	 * @param event
	 */
	public void loadStockByDate(ActionEvent event)
	{
		String exchange = "";
		String date = "";

		if (amexRadioButton.isSelected())
		{
			exchange = "Amex";
		} else if (nyseRadioButton.isSelected())
		{
			exchange = "nyse";
		} else if (nasdaqRadioButton.isSelected())
		{
			exchange = "nasdaq";
		} else
		{
			exchange = "forex";
		}
		
		date = comboBox.getSelectionModel().getSelectedItem().toString();
		
		Symbol.setCellValueFactory(new PropertyValueFactory<Stock, String>("symbol"));
		Date.setCellValueFactory(new PropertyValueFactory<Stock, String>("date"));
		Open.setCellValueFactory(new PropertyValueFactory<Stock, String>("open"));
		High.setCellValueFactory(new PropertyValueFactory<Stock, String>("high"));
		Low.setCellValueFactory(new PropertyValueFactory<Stock, String>("low"));
		Close.setCellValueFactory(new PropertyValueFactory<Stock, String>("close"));
		Volume.setCellValueFactory(new PropertyValueFactory<Stock, String>("volume"));
		c2.setCellValueFactory(new PropertyValueFactory<Stock, String>("c2"));
		tableView.getItems().setAll(new DatabaseCalls().getStocksByDateAndExchange(exchange, date));
	}

}
