package test;

import database.ReadWriteData;

public class Test {

	public static void main(String[] args) {
		ReadWriteData readWriteData = new ReadWriteData();
		
		readWriteData.parseDate("20160905","yyyyddmm");
	}

}
