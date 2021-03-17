package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {

	private final int number = Math.abs(new Random().nextInt());
	private Map<Product, Integer> products = new LinkedHashMap<>();
	private final Calendar date;
	
	public Invoice() {
		date = Calendar.getInstance();
	}
	
	public Invoice(Calendar c) {
		date = c;
	}
	
	public void addProduct(Product product) {
		this.products.put(product, 1);

	}

	public void addProduct(Product product,Integer quantity) {
		if (product == null || quantity <= 0) {
			throw new IllegalArgumentException();
		}
		if (products.containsKey(product)) {
			products.put(product, products.get(product) + quantity);
		} else {
			products.put(product, quantity);
		}

	}

	public BigDecimal getSubtotal() {
		BigDecimal sum = BigDecimal.ZERO;

		for (Product product : this.products.keySet()) {
			BigDecimal quantity = new BigDecimal(this.products.get(product));

			sum = sum.add(product.getPrice().multiply((quantity)));

		}

		return sum;

	}

	public BigDecimal getTax() {

		return (getTotal().subtract(getSubtotal()));
	}

	public BigDecimal getTotal() {
		BigDecimal totalValue = BigDecimal.ZERO;

		for (Product product : this.products.keySet()) {
			BigDecimal quantity =new BigDecimal( this.products.get(product));

			totalValue = totalValue.add((product.getPriceWithTax(date).multiply((quantity))));

		}

		return totalValue;

	}

	public int getNumber() {
		return number;

	}

	public int getProducts() {
		Invoice invoice = new Invoice();
		int N = products.size();
		System.out.println("Nr faktury: " + invoice.getNumber());
		for (Product product : products.keySet()) {
			System.out.println(product.getName() + " ilość: " + products.get(product) + " cena: "
					+ product.getPriceWithTax(date) + " zł");
		}
		System.out.println("Liczba pozycji: " + N);
		return N;
	}

	public int getProductQuantity(Product product) {

		int quantity = 0;
		for (Product product1 : products.keySet()) {
			quantity = products.get(product);
		}

		return quantity;
	}
}
