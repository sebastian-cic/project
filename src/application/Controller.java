package application;

import database.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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
	private TextField symbolText;
	@FXML
	private TextField dateText;
	@FXML
	private TextField openText;
	@FXML
	private TextField highText;
	@FXML
	private TextField lowText;
	@FXML
	private TextField closeText;
	@FXML
	private TextField volumeText;
	@FXML
	private TextField exchangeText;

	@FXML
	public void initialize()
	{
		// load amex data to table for most recent data
		Symbol.setCellValueFactory(new PropertyValueFactory<Stock, String>("symbol"));
		Date.setCellValueFactory(new PropertyValueFactory<Stock, String>("date"));
		Open.setCellValueFactory(new PropertyValueFactory<Stock, String>("open"));
		High.setCellValueFactory(new PropertyValueFactory<Stock, String>("high"));
		Low.setCellValueFactory(new PropertyValueFactory<Stock, String>("low"));
		Close.setCellValueFactory(new PropertyValueFactory<Stock, String>("close"));
		Volume.setCellValueFactory(new PropertyValueFactory<Stock, String>("volume"));
		c2.setCellValueFactory(new PropertyValueFactory<Stock, String>("c2"));
		tableView.getItems().setAll(new DatabaseCalls().getTable(new DatabaseCalls().getNewestDate()));
		// get all available dates for stock data into combo box
		comboBox.setItems(new DatabaseCalls().getAllDates());
		comboBox.setValue("Date");
		comboBox.getSelectionModel().selectFirst();
	}

	/**
	 * load stock data from CSV files to database.File -> load CSV to database
	 * in menu bar
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
	 * 
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

		tableView.getItems().setAll(new DatabaseCalls().getStocksByDateAndExchange(exchange, date));
	}

	/**
	 * Get all data for one stock by double clicking on table column
	 * 
	 * @param event
	 */
	public void getSpecificStock(MouseEvent event)
	{
		String exchange = "";
		Stock stock = new Stock();
		stock = tableView.getSelectionModel().getSelectedItem();

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
		// Primary button double clicked, excluding header cells.
		if (stock != null && event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY)
		{
			tableView.getItems().setAll(new DatabaseCalls().getSpecificStock(exchange, stock.getSymbol()));
		} else if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1 && stock != null)
		{
			stock = tableView.getSelectionModel().getSelectedItem();

			symbolText.setText(stock.getSymbol());
			dateText.setText(stock.getDate());
			openText.setText(stock.getOpen());
			highText.setText(stock.getHigh());
			lowText.setText(stock.getLow());
			closeText.setText(stock.getClose());
			volumeText.setText(stock.getVolume());
			exchangeText.setText(exchange);
		}
	}

	public void printMA(ActionEvent actionEvent)
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

		TechnicalSignals ts = new TechnicalSignals();
		//ts.simpleMovingAverage(10, exchange, date, "aau");
		ts.getMACrossover("aau", "amex");
	}

}
