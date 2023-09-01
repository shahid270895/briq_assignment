package briq_C_assignment;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import com.opencsv.CSVWriter;

public class fetch_data_from_api_library_and_export_to_csv {

	static String project_path = System.getProperty("user.dir");
	static String urlStr = "https://data.sfgov.org/resource/p4e4-a5a7.json";

	public static void main(String[] args) {
		try {
			URL url = new URL(urlStr);

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");

			if (connection.getResponseCode() == 200) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				StringBuilder response = new StringBuilder();
				String line;
				while ((line = reader.readLine()) != null) {
					response.append(line);
				}
				reader.close();

				JSONArray jsonArray = new JSONArray(response.toString());

				CSVWriter csvWriter = new CSVWriter(new FileWriter(project_path+"\\Home\\briq\\sfgov_"+ get_current_date_and_time() +".csv"));

				JSONObject firstObject = jsonArray.getJSONObject(0);
				String[] headers = firstObject.keySet().toArray(new String[0]);
				csvWriter.writeNext(headers);

				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					String[] row = new String[headers.length];
					for (int j = 0; j < headers.length; j++) {
						row[j] = jsonObject.optString(headers[j], "");
					}
					csvWriter.writeNext(row);
				}

				csvWriter.close();

				String jsonFileName = project_path+"\\Home\\briq\\sfgov_" + get_current_date_and_time() + ".json";
				
				FileWriter jsonFileWriter = new FileWriter(jsonFileName);
				jsonFileWriter.write(jsonArray.toString(4));
				jsonFileWriter.close();

			} else {
				System.out.println("Failed to fetch data from the URL. HTTP Status Code: " + connection.getResponseCode());
			}
			System.out.println("Export to csv file done. Kindly check the file...");
			connection.disconnect();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String get_current_date_and_time() {
		DateFormat customFormat=new SimpleDateFormat("MM-dd-yyyy-HH-mm-ss");
		Date currentDate=new Date();
		return customFormat.format(currentDate);
	}      


}
