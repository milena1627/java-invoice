package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pl.edu.agh.mwo.invoice.Invoice;
import pl.edu.agh.mwo.invoice.product.BottleOfWine;
import pl.edu.agh.mwo.invoice.product.DairyProduct;
import pl.edu.agh.mwo.invoice.product.FuelCanister;
import pl.edu.agh.mwo.invoice.product.OtherProduct;
import pl.edu.agh.mwo.invoice.product.Product;
import pl.edu.agh.mwo.invoice.product.TaxFreeProduct;

public class InvoiceTest {
    private Invoice invoice;

    @Before
    public void createEmptyInvoiceForTheTest() {
        invoice = new Invoice();
    }

    @Test
    public void testEmptyInvoiceHasEmptySubtotal() {
        Assert.assertThat(BigDecimal.ZERO, Matchers.comparesEqualTo(invoice.getSubtotal()));
    }

    @Test
    public void testEmptyInvoiceHasEmptyTaxAmount() {
        Assert.assertThat(BigDecimal.ZERO, Matchers.comparesEqualTo(invoice.getTax()));
    }

    @Test
    public void testEmptyInvoiceHasEmptyTotal() {
        Assert.assertThat(BigDecimal.ZERO, Matchers.comparesEqualTo(invoice.getTotal()));
    }

    @Test
    public void testInvoiceHasTheSameSubtotalAndTotalIfTaxIsZero() {
        Product taxFreeProduct = new TaxFreeProduct("Warzywa", new BigDecimal("199.99"));
        invoice.addProduct(taxFreeProduct);
        Assert.assertThat(invoice.getTotal(), Matchers.comparesEqualTo(invoice.getSubtotal()));
    }

    @Test
    public void testInvoiceHasProperSubtotalForManyProducts() {
        invoice.addProduct(new TaxFreeProduct("Owoce", new BigDecimal("200")));
        invoice.addProduct(new DairyProduct("Maslanka", new BigDecimal("100")));
        invoice.addProduct(new OtherProduct("Wino", new BigDecimal("10")));
        Assert.assertThat(new BigDecimal("310"), Matchers.comparesEqualTo(invoice.getSubtotal()));
    }

    @Test
    public void testInvoiceHasProperTaxValueForManyProduct() {
        // tax: 0
        invoice.addProduct(new TaxFreeProduct("Pampersy", new BigDecimal("200")));
        // tax: 8
        invoice.addProduct(new DairyProduct("Kefir", new BigDecimal("100")));
        // tax: 2.30
        invoice.addProduct(new OtherProduct("Piwko", new BigDecimal("10")));
        Assert.assertThat(new BigDecimal("10.30"), Matchers.comparesEqualTo(invoice.getTax()));
    }

    @Test
    public void testInvoiceHasProperTotalValueForManyProduct() {
        // price with tax: 200
        invoice.addProduct(new TaxFreeProduct("Maskotki", new BigDecimal("200")));
        // price with tax: 108
        invoice.addProduct(new DairyProduct("Maslo", new BigDecimal("100")));
        // price with tax: 12.30
        invoice.addProduct(new OtherProduct("Chipsy", new BigDecimal("10")));
        Assert.assertThat(new BigDecimal("320.30"), Matchers.comparesEqualTo(invoice.getTotal()));
    }

    @Test
    public void testInvoiceHasPropoerSubtotalWithQuantityMoreThanOne() {
        // 2x kubek - price: 10
        invoice.addProduct(new TaxFreeProduct("Kubek", new BigDecimal("5")), 2);
        // 3x kozi serek - price: 30
        invoice.addProduct(new DairyProduct("Kozi Serek", new BigDecimal("10")), 3);
        // 1000x pinezka - price: 10
        invoice.addProduct(new OtherProduct("Pinezka", new BigDecimal("0.01")), 1000);
        Assert.assertThat(new BigDecimal("50"), Matchers.comparesEqualTo(invoice.getSubtotal()));
    }

    @Test
    public void testInvoiceHasPropoerTotalWithQuantityMoreThanOne() {
        // 2x chleb - price with tax: 10
        invoice.addProduct(new TaxFreeProduct("Chleb", new BigDecimal("5")), 2);
        // 3x chedar - price with tax: 32.40
        invoice.addProduct(new DairyProduct("Chedar", new BigDecimal("10")), 3);
        // 1000x pinezka - price with tax: 12.30
        invoice.addProduct(new OtherProduct("Pinezka", new BigDecimal("0.01")), 1000);
        Assert.assertThat(new BigDecimal("54.70"), Matchers.comparesEqualTo(invoice.getTotal()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvoiceWithZeroQuantity() {
        invoice.addProduct(new TaxFreeProduct("Tablet", new BigDecimal("1678")), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvoiceWithNegativeQuantity() {
        invoice.addProduct(new DairyProduct("Zsiadle mleko", new BigDecimal("5.55")), -1);
    }
    
    @Test
	public void testInvoiceHasNumber() {
		int number = invoice.getNumber();
		Assert.assertTrue(number > 0);
	}

	@Test
	public void testTwoInvoicesHaveDifferentNumbers() {

		int number = invoice.getNumber();
		int number2 = new Invoice().getNumber();
		Assert.assertNotEquals(number, number2);

	}

	@Test
	public void testTheSameInvoiceHasTheSameNumber() {

		Assert.assertEquals(invoice.getNumber(), invoice.getNumber());
	}
	
	
	@Test
	public void testGetListOfProducts() {
		invoice.addProduct(new DairyProduct("Maślanka", new BigDecimal("5")), 2);
		invoice.addProduct(new OtherProduct("Ogórek", new BigDecimal("5")), 2);
		invoice.addProduct(new OtherProduct("Gruszka", new BigDecimal("5")), 2);
			
		Assert.assertEquals(invoice.getProducts(),3);
	}
	
	 @Test
	    public void checkIfDuplicatingProductOnInvoice() {
	        Product np = new OtherProduct("Pączek", new BigDecimal("2.30"));
	        invoice.addProduct(np, 1);
	        Assert.assertEquals(1, invoice.getProductQuantity(np));
	        invoice.addProduct(np, 4);
	        Assert.assertEquals(5,invoice.getProductQuantity(np));
	    }
	 
	 
	 @Test
		public void testCheckIfExciseAdded() {
			
			invoice.addProduct(new BottleOfWine("Rosse",new BigDecimal("70")), 1);
			invoice.addProduct(new FuelCanister("Verva",new BigDecimal("150")), 1);
			
			Assert.assertEquals(new BigDecimal("248.72"), invoice.getTotal());
		}
	 
	 @Test
		public void testCheckIfDriverFeeDecreasedTax() {
		 	Calendar c = new GregorianCalendar(2021,11,18);
		 	invoice = new Invoice(c);
			invoice.addProduct(new BottleOfWine("Rosse",new BigDecimal("70")), 1);
			invoice.addProduct(new FuelCanister("Verva",new BigDecimal("150")), 1);
			
			Assert.assertEquals(new BigDecimal("17.60"), invoice.getTax());
		}
	 
	 
	 
	 
}
