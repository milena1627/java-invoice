package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public abstract class Product {
	private final String name;

	private final BigDecimal price;

	private final BigDecimal taxPercent;

	private final BigDecimal excise;
	
	private final Calendar date = new GregorianCalendar(2000,11,18);

	protected Product(String name, BigDecimal price, BigDecimal tax, BigDecimal excise) {
		if (name == null || name.equals("") || price == null || tax == null || excise == null
				|| tax.compareTo(new BigDecimal(0)) < 0 || price.compareTo(new BigDecimal(0)) < 0) {
			throw new IllegalArgumentException();
		}
		this.name = name;
		this.price = price;
		this.taxPercent = tax;
		this.excise = excise;
	}

	public String getName() {
		return name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public BigDecimal getTaxPercent() {
		return taxPercent;
	}

	public BigDecimal getPriceWithTax(Calendar c) {
		BigDecimal px = (price.multiply(taxPercent).add(price));
		
		if (c.get(Calendar.DAY_OF_MONTH)==date.get(Calendar.DAY_OF_MONTH)
				&& c.get(Calendar.MONTH)==date.get(Calendar.MONTH)) {
			return px;
		} else {

			return (px.add(excise));
		}

	}

}
