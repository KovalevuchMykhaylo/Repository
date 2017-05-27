package intelliartsTraineeTest.view;

import intelliartsTraineeTest.controller.Controller;
import intelliartsTraineeTest.model.PLN;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;

public class Main {
	//Створює буфер для читання данних з клавіатури
	private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws IOException {
		//Екземпляр контроллера
		Controller controller = new Controller();
		//Цикл буде повторюватись до помилки
		while (true) {
			//Невиличка інструкція
			System.out.println("Enter 'add' to item to expense list. EXEMPLE: add 2017-04-25 12 USD Jogurt");
			System.out.println("Enter 'list' to show all expense");
			System.out.println("Enter 'clear' removes all expenses for specified date. EXEMPLE: clear 2017-04-25");
			System.out.println("Enter 'total PLN' to show total amount of expenses. EXEMPLE total USD");
			//Ініціалізація буфера
			String addcase = reader.readLine();
			//Ініціалізація змінних класу
			String[] addrr = addcase.split("[\\ \\.\\'\\,\\:]+");
			String casecomand = addrr[0].toString().trim();
			String date = null;
			BigDecimal amount = null;
			PLN pln = null;
			String name = null;
			//Виклик випадку із ініціалізацією змінних
			if (casecomand.equalsIgnoreCase("add")) {
				controller.parser(addcase);
			} else if (casecomand.equalsIgnoreCase("clear")) {
				casecomand = addrr[0];
				date = addrr[1];
			} else if (casecomand.equalsIgnoreCase("total PLN")) {
				casecomand = addrr[0];
				pln = PLN.valueOf(addrr[1]);
			}

			switch (casecomand) {
			case ("add"):
				//Додатковий парсінг для створення витрати 
				int index1 = addcase.indexOf(" ");
				casecomand = addcase.substring(0, index1).trim();
				date = addcase.substring(index1 + 1, index1 + 11).trim();
				String item = addcase.substring(index1 + 12).trim();
				int index2 = item.indexOf(" ");
				String amount1 = item.substring(0, index2).trim();
				String PL = item.substring(index2 + 1, index2 + 4).trim();
				String name1 = item.substring(index2 + 5).trim().replace("\"", "");

				controller.addItem(date, new BigDecimal(amount1), PLN.valueOf(PL), name1);
				controller.showAll();

				break;
			case ("list"):
				//Показати всі витрати
				controller.showAll();

				break;
			case ("clear"):
				//Видалити витрати по даті
				controller.clearDate(date);
				controller.showAll();

				break;
			case ("total"):
				//Вивести в консоль всі витрати у заданій валюті(грошовій одиниці)
				System.out.println(controller.totalAllPLN(pln, addrr[1]));

				break;

			default:
				break;
			}
		}
	}
}
