package intelliartsTraineeTest.model;

import java.math.BigDecimal;
//Об'єкт (витрата, покупка)
public class Item {
	//Назва
	private String name;
	//Ціна
	private BigDecimal amount;
	//Грошові одиниці (валюта)
	private PLN pln;

	public Item(BigDecimal amount, PLN pln, String name) {
		this.name = name;
		this.amount = amount;
		this.pln = pln;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public PLN getPln() {
		return pln;
	}

	public void setPln(PLN pln) {
		this.pln = pln;
	}

	@Override
	public String toString() {
		return name + " " + amount + " " + pln;
	}

}
