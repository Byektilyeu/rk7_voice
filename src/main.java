import java.io.FileWriter;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

public class main {

	public static void main(String[] args) throws Exception {

		new Timer().scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				try {
					mainFunction();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, 0, 10000);
	}

	private static void mainFunction() throws Exception {
		try {
			sslDisable ssl = new sslDisable();
			ssl.disableSslVerification();

			requestGetOrderList getOrderList = new requestGetOrderList();
			String result = getOrderList.postMessageGetOrderList();
			
			

			

			String qmsNum = null;

			JSONObject jsonGetOrderList = XML.toJSONObject(result);
			
			
			
			
			JSONObject RK7QueryResultOrderList = (JSONObject) jsonGetOrderList.get("RK7QueryResult");
			JSONObject CommandResultOrderList = (JSONObject) RK7QueryResultOrderList.get("CommandResult");
			JSONArray arrVisit = (JSONArray) CommandResultOrderList.getJSONArray("Visit");
			
			for (int i = 0; i < arrVisit.length(); i++) {
				
				JSONObject Visit = (JSONObject) arrVisit.getJSONObject(i);
				JSONObject Orders = (JSONObject) Visit.get("Orders");
				JSONObject Orderr = (JSONObject) Orders.get("Order");
				
				String guid = (String) Orderr.get("guid");
//				JSONObject guid = (JSONObject) Orderr.get("guid");
//				String guidString = guid.toString();
				
				requestGetOrder getOrder = new requestGetOrder();
				String response = getOrder.postMessageGetOrder(guid);
				JSONObject jsonGetOrder = XML.toJSONObject(response);
				
				
				JSONObject RK7QueryResult = (JSONObject) jsonGetOrder.get("RK7QueryResult");
				JSONObject CommandResult = (JSONObject) RK7QueryResult.get("CommandResult");
				JSONObject Order = (JSONObject) CommandResult.get("Order");
				JSONObject ExternalProps = (JSONObject) Order.get("ExternalProps");

				JSONArray arrProp = (JSONArray) ExternalProps.getJSONArray("Prop");
				for (int j = 0; j < arrProp.length(); j++) {
					JSONObject Prop = (JSONObject) arrProp.getJSONObject(j);
					String qmsNumber = (String) Prop.get("value");
					if (qmsNumber.length() == 3) {
						qmsNum = qmsNumber;
					}
				}

				String kdsstate = (String) Order.get("kdsstate");
				int visit1 = (int) Order.get("visit");

				System.out.println("GETORDER XML DATA: " + response);
				System.out.println("GETORDER JSON DATA: " + jsonGetOrder);
				System.out.println("GETORDERLIST JSON DATA: " + jsonGetOrderList);
				System.out.println("visit: " + visit1);
				System.out.println("kdsstate: " + kdsstate);
				System.out.println("qmsNumber: " + qmsNum);

				FileWriter fw = new FileWriter("C:\\Users\\Lenovo T470\\Desktop\\RK7Voice\\test.txt", true);

				fw.write(kdsstate + "/");
				fw.write(String.valueOf(visit1) + "/");
				fw.write(qmsNum + "/");
				fw.write(System.lineSeparator());
				fw.close();
			}
			
			

			

		} catch (IOException ex) {
			System.out.println("=======================" + ex);
		}
	}
}
