package intelliartsTraineeTestJunitTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import intelliartsTraineeTest.controller.Controller;
import intelliartsTraineeTest.model.Item;
import intelliartsTraineeTest.model.PLN;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

public class ContrillerTest {

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void parser() {
		Controller controller = new Controller();
		String[] test = controller.parser("add 2017-04-25 12 USD Jogurt​");
		Assert.assertArrayEquals(test,
				Controller.parser("add 2017-04-25 12 USD Jogurt​"));
	}

	@Test
	public void showAll() {
		Controller controller = new Controller();
		controller.addItem("2020-10-10", new BigDecimal(10), PLN.USD, "Test");
		Map<String, List<Item>> maptest = new TreeMap<>(controller.MAP);
		Map<String, List<Item>> map = new TreeMap<>();
		List<Item> list = new ArrayList<>();
		list.add(new Item(new BigDecimal(10), PLN.USD, "Test"));
		map.put("2020-10-10", list);
		Assert.assertEquals(map, maptest);
	}

	@Test
	public void clearDate() {
		Controller controller = new Controller();
		controller.addItem("2020-10-10", new BigDecimal(10), PLN.USD, "Test");
		controller.clearDate("2020-10-10");
		Map<String, List<Item>> maptest = new HashMap<>(controller.MAP);
		Map<String, List<Item>> map = new HashMap<>();
		Assert.assertEquals(map, maptest);
	}
}
