package intelliartsTraineeTest.controller;

import intelliartsTraineeTest.model.Item;
import intelliartsTraineeTest.model.PLN;
import intelliartsTraineeTest.util.JsonUtils;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Controller {
	//Створюємо мапу: дата об'єкт
	public static final Map<String, List<Item>> MAP = new HashMap<>();
	//Створюємо первинну силку пез вказання конкретної валюти
	public static final String FIXER = "http://api.fixer.io/latest?base=";
	//Парсимо вхідну стрічку
	public static String[] parser(String str) {
		int index1 = str.indexOf(" ");
		//Виділення з стрічки команда свіча
		String casecomand = str.substring(0, index1).trim();
		//Виділення дати
		String date = str.substring(index1 + 1, index1 + 11).trim();
		//Виділення об'єкту з стрічки
		String item = str.substring(index1 + 12).trim();
		int index2 = item.indexOf(" ");
		String amount = item.substring(0, index2).trim();
		String PLN = item.substring(index2 + 1, index2 + 4).trim().toUpperCase();
		//Ім'я об'єкту можу містити пробіли і складатись з декількох слів
		String name = item.substring(index2 + 5, item.length()).trim();
		String[] parsRes = { casecomand, date, amount, PLN, name };
		return parsRes;
	}
	//Мотод дадає об'єкт типу "Item"
	public static void addItem(String date, BigDecimal amount, PLN pln,	String name) {
		//Якщо по ключу вже є об'єкти
		List<Item> items = MAP.get(new String(date));
		if (items != null) {
			items.add(new Item(amount, pln, name));
		//Якщо ключа не знайдено
		} else {
			List<Item> items2 = new ArrayList<>();
			items2.add(new Item(amount, pln, name));
			MAP.put(new String(date), items2);
		}
	}
	//Відображення всіх витрат
	public static void showAll() {
		//Приводить до сортованого вигляду
		Map<String, Object> treeMap = new TreeMap<String, Object>(MAP);
		//Дії з ключем та значенням
		for (Map.Entry entry : treeMap.entrySet()) {
			//Витягуємо ключ
			String key = (String) entry.getKey();
			//Витягуємо всі значення по ключу
			List<Item> value = (List<Item>) entry.getValue();
			//Вивід на екран ключ і всі його значення
			System.out.println(key);
			value.forEach(System.out::println);
		}
	}
	//Видаляє всі об'єкти(витрати) за вказаною датою
	public static void clearDate(String date) {
		Set<String> keys = MAP.keySet();
		for (String key : keys) {
			if (key.equalsIgnoreCase(date)) {
				keys.remove(date);
			}
		}
	}
	//Метод конвертує всі витрати в одну валюту і виводить на екран
	public static BigDecimal totalAllPLN(PLN pln, String addrrPLN) {
		//Створення мапи для метода
		Map<String, Object> treeMap1 = new TreeMap<String, Object>(MAP);
		//Ініціалізяція змінних метода
		double total = 0;
		double total1 = 0;
		double totalall = 0;
		double course = 0;
		//Витягує з мапи ліс значень
		for (Map.Entry entry : treeMap1.entrySet()) {
			List<Item> value = (List<Item>) entry.getValue();
			Iterator<Item> iter = value.iterator();
			//Створює ссилку із урахування вказаної валюти
			URL url = JsonUtils.createUrl(FIXER + addrrPLN);
			// завантажує Json у вигляді Java стрічки
			String resultJson = JsonUtils.parseUrl(url);
			//Переводить стрічку у масив та позбувається зайвих знаків
			String[] strr = resultJson.split("[\\ \\'\\,\\:\\{\\}\\\"]+");
			//Гортає ліст значень
			while (iter.hasNext()) {
				Item item = iter.next();
				//Якщо в значення курс в не тій валюті яка потрібна
				if (!item.getPln().equals(pln)) {
					for (int i = 0; i < strr.length; i++) {
						//Пошук потрібної валюти
						if (strr[i].equalsIgnoreCase(item.getPln().toString())	& strr[i] != strr[2]) {
							//Створення курсу
							course = Double.valueOf(strr[i + 1]);
							//Конвертація цін по курсу
							total = total + item.getAmount().doubleValue()	/ course;
							//Якщо об'єк вже у потрібній валюті
						} else if (strr[i].equalsIgnoreCase(item.getPln()
								.toString()) & strr[i] == strr[2]) {
							total1 = total1 + item.getAmount().doubleValue();
						}
					}
				}
				//Загальна сума
				totalall = total + total1;
			}
		}
		//Загальна сума з заокругленням, тільки два знаки після коми
		return new BigDecimal(totalall).setScale(2, BigDecimal.ROUND_UP);
	}
}
