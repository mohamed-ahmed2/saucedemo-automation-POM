package utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.json.Json;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JsonReader {

        public static String getTestData(String key, String subKey) {
            try {
                // مسار الملف (تأكد إنه صح حسب تقسيم الفولدرات عندك)
                String filePath = "src/test/resources/testData.json";
                FileReader jsonFile = new FileReader(filePath);
                JSONParser parser = new JSONParser();
                JSONObject jsonObject = (JSONObject) parser.parse(jsonFile);

                // بنجيب الـ Object الكبير (مثلاً validUser) ومنه نجيب الـ Value (مثلاً username)
                JSONObject innerObject = (JSONObject) jsonObject.get(key);
                return (String) innerObject.get(subKey);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        public static Object[][] getJsonArrayData(String fileName, String arrayName) throws IOException, ParseException {
            String filePath = "src/test/resources/";
            FileReader jsonFile = new FileReader(filePath+fileName);
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonFile);
            JSONArray jsonArray =(JSONArray) jsonObject.get(arrayName);
            Object[][] data = new Object[jsonArray.size()][1];

            for (int i=0;i<jsonArray.size();i++){
            data[i][0] = jsonArray.get(i);


            }
            return data;
        }
    }

