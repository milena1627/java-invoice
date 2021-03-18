package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;

import pl.edu.agh.mwo.invoice.product.BottleOfWine;
import pl.edu.agh.mwo.invoice.product.FuelCanister;
import pl.edu.agh.mwo.invoice.product.OtherProduct;
import pl.edu.agh.mwo.invoice.product.Product;

public class Main {

	public static void main(String[] args) {

		Invoice invoice1 = new Invoice();
		invoice1.addProduct(new OtherProduct("kaktus", new BigDecimal("11.50")), 1);
		invoice1.addProduct(new OtherProduct("figurka", new BigDecimal("41.00")), 1);

		Product np = new OtherProduct("Pączek", new BigDecimal("2.30"));
		invoice1.addProduct(np, 1);
		invoice1.addProduct(np, 5);
		invoice1.getProducts();

		Invoice invoice2 = new Invoice();
		invoice2.addProduct(new BottleOfWine("rosso", new BigDecimal("17.00")), 1);
		invoice2.addProduct(new FuelCanister("diesel", new BigDecimal("54.00")), 1);

		BigDecimal totalAmount = invoice2.getTotal();
		System.out.println("Cena produktów z podatkiem i akcyzą wynosi razem: " + totalAmount + " zł");
		
		Calendar c = new GregorianCalendar(2021,11,18);
	 	Invoice invoice3 = new Invoice(c);
		invoice3.addProduct(new BottleOfWine("Rosse",new BigDecimal("70")), 1);
		invoice3.addProduct(new FuelCanister("Verva",new BigDecimal("150")), 1);
		
		BigDecimal totalTaxwithoutExcise = invoice3.getTax();
		System.out.println("Podatek za produkty bez akcyzy z okazji dnia transportowca wynosi razem: " + totalTaxwithoutExcise + " zł");
		
		BigDecimal totalAmountwithoutExcise = invoice3.getTotal();
		System.out.println("Cena za produkty bez akcyzy z okazji dnia transportowca wynosi razem: " + totalAmountwithoutExcise + " zł");
	}

}
