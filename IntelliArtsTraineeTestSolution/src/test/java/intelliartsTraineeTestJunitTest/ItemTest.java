package intelliartsTraineeTestJunitTest;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import intelliartsTraineeTest.model.Item;
import intelliartsTraineeTest.model.PLN;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

public class ItemTest {

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test() {
		Item item = new Item(new BigDecimal(10), PLN.USD, "Test");
		BigDecimal amount = new BigDecimal(10);
		PLN pln = PLN.USD;
		String name = "Test";
		Assert.assertEquals(amount, item.getAmount());
		Assert.assertEquals(pln, item.getPln());
		Assert.assertEquals(name, item.getName());
	}

}
