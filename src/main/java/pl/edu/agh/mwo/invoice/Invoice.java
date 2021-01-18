package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {

	private Map<Product, Integer> products = new LinkedHashMap<>();

	public void addProduct(Product product) {
		this.products.put(product, 1);

	}

	public void addProduct(Product product, Integer quantity) {
		this.products.put(product, quantity);

		if (quantity == 0 || quantity.signum(quantity) == -1) {

			throw new IllegalArgumentException("You cannot add quantity as 0 or negative");
		}
	}

	public BigDecimal getSubtotal() {
		BigDecimal sum = BigDecimal.ZERO;

		for (Product product : this.products.keySet()) {
			Integer quantity = this.products.get(product);

			sum = sum.add(product.getPrice().multiply(new BigDecimal(quantity)));

		}

		return sum;

	}

	public BigDecimal getTax() {

		BigDecimal tax = BigDecimal.ZERO;

		for (Product product : this.products.keySet()) {
			tax = tax.add(product.getTaxPercent().multiply(product.getPrice()));

		}
		return tax;

	}

	public BigDecimal getTotal() {
		BigDecimal totalValue = BigDecimal.ZERO;

		for (Product product : this.products.keySet()) {
			Integer quantity = this.products.get(product);

			totalValue = totalValue.add((product.getPriceWithTax().multiply(new BigDecimal(quantity))));

		}

		return totalValue;

	}
}
