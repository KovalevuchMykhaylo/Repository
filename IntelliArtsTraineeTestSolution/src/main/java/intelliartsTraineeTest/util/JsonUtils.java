package intelliartsTraineeTest.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class JsonUtils {
	public static String parseUrl(URL url) {
		if (url == null) {
			return "";
		}
		StringBuilder stringBuilder = new StringBuilder();
		// Відкриває з'єднання до вказаного URL
		try (BufferedReader in = new BufferedReader(new InputStreamReader(
				url.openStream()))) {

			String inputLine;
			//Пострічкова считка результатів в об'єкт StringBuilder
			while ((inputLine = in.readLine()) != null) {
				stringBuilder.append(inputLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stringBuilder.toString();
	}

	// Створюємо об'єкт URL з вказаної в параметрі стрічки
	public static URL createUrl(String link) {
		try {
			return new URL(link);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}

}