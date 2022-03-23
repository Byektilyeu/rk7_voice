import java.io.FileWriter;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

public class main {

	public static void main(String[] args) throws Exception {

		// 3 секундад нэг удаа, сервер рүү хүсэлт илгээдэг функцүүдийг дуудаж
		// ажиллуулдаг функц
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
		}, 0, 1000);
	}

	// Үндсэн функц
	private static void mainFunction() throws Exception {
		try {
			String qmsNum = null;
			String kdsstate = null;

			sslDisable ssl = new sslDisable();
			ssl.disableSslVerification();
			
			
			dbConnection database = new dbConnection();
			database.createNewDb();
			database.createNewTable();

			// GetOrderList class-ийн postMessageGetOrderList() функцийг дуудаж getOrderList
			// хүсэлтийн хариу өгөгдлүүдийг result хувьсагчид олгох
			requestGetOrderList getOrderList = new requestGetOrderList();
			String result = getOrderList.postMessageGetOrderList();

			// Result-ийн утгыг Json хэлбэртэй өгөгдөл болгож төрөл хувиргалт хийх
			JSONObject jsonGetOrderList = XML.toJSONObject(result);

			// Json өгөгдлийг задалж, захиалга тус бүрээр нь давтах
			JSONObject RK7QueryResultOrderList = (JSONObject) jsonGetOrderList.get("RK7QueryResult");
			JSONObject CommandResultOrderList = (JSONObject) RK7QueryResultOrderList.get("CommandResult");
			JSONArray arrVisit = (JSONArray) CommandResultOrderList.getJSONArray("Visit");

			for (int i = 0; i < arrVisit.length(); i++) {
				JSONObject Visit = (JSONObject) arrVisit.getJSONObject(i);
				JSONObject Orders = (JSONObject) Visit.get("Orders");
				JSONObject Orderr = (JSONObject) Orders.get("Order");

				// Тухайн захиалгад харгалзах QUID утгыг авч, сервер рүү хүсэлт илгээх
				String guid = (String) Orderr.get("guid");

				requestGetOrder getOrder = new requestGetOrder();
				String response = getOrder.postMessageGetOrder(guid);

				JSONObject jsonGetOrder = XML.toJSONObject(response);

				// GetOrder-ийн json өгөгдлийг задалж хэрэгтэй өгөдлүүдийг авах
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

				int visit1 = (int) Order.get("visit");

				System.out.println("GETORDER XML DATA: => " + response);
				System.out.println("GETORDER JSON DATA: => " + jsonGetOrder);
				System.out.println("GETORDERLIST JSON DATA: => " + jsonGetOrderList);
				System.out.println("visit: => " + visit1);
				System.out.println("qmsNumber: => " + qmsNum);

				// kdsstate baigaa esehiig shalgaj bna
				if (Order.has("kdsstate")) {
					// get Value of video
					kdsstate = (String) Order.get("kdsstate");
					System.out.println("kdsstate: => " + kdsstate);
				} else {
					kdsstate = null;
					System.out.println("!!! Уучлаарай, " + qmsNum + " захиалгын өгөгдөлд kdsstate олдоогүй !!!");
					System.out.println("!!! Уучлаарай, " + qmsNum + " захиалгын өгөгдөлд kdsstate олдоогүй !!!");
				}
				
				InsertApp app = new InsertApp();
				app.insert(visit1, qmsNum, kdsstate );

				// text file руу бичиж, хадгалах
				FileWriter fw = new FileWriter("C:\\Users\\Lenovo T470\\Desktop\\RK7Voice\\test.txt", true);
				fw.write(kdsstate + "/");
				fw.write(String.valueOf(visit1) + "/");
				fw.write(qmsNum + "/");
				fw.write(System.lineSeparator());
				fw.close();
			}
		} catch (IOException ex) {
			System.out.println("Алдаа гарлаа: => " + ex);
		}
	}
}
