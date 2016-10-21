package application;

import database.*;

public class Controller {

	public void loadCSVToDataBase(){
		System.out.println("loading");
		ReadWriteData rw = new ReadWriteData();
		rw.readInCSVFile();
	 }
}
