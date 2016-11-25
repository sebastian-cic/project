package stock;

public class Stock
{

	public Stock()
	{
		super();
	}
	public String getSymbol()
	{
		return symbol;
	}
	public void setSymbol(String symbol)
	{
		this.symbol = symbol;
	}
	public String getDate()
	{
		return date;
	}
	public void setDate(String date)
	{
		this.date = date;
	}
	public String getOpen()
	{
		return open;
	}
	public void setOpen(String open)
	{
		this.open = open;
	}
	public String getHigh()
	{
		return high;
	}
	public void setHigh(String high)
	{
		this.high = high;
	}
	public String getLow()
	{
		return low;
	}
	public void setLow(String low)
	{
		this.low = low;
	}
	public String getClose()
	{
		return close;
	}
	public void setClose(String close)
	{
		this.close = close;
	}
	public String getVolume()
	{
		return volume;
	}
	public void setVolume(String volume)
	{
		this.volume = volume;
	}
	public String getC2()
	{
		return ma;
	}
	public void setC2(String c2)
	{
		this.ma = c2;
	}
	public Stock(String symbol, String date, String open, String high, String low, String close, String volume,
			String c2)
	{
		this.symbol = symbol;
		this.date = date;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.volume = volume;
		this.ma = c2;
	}
	public String symbol;
	public String date;
	public String open;
	public String high;
	public String low;
	public String close;
	public String volume;
	public String ma;
}
