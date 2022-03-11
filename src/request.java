
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
 
public class request {
 
	public String postMessage() throws IOException {
		final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) Version/7.0.3 Safari/7046A194A";
		final StringBuffer content = new StringBuffer();
//		
//		final String parameters = "?name=tecbar&group=middleschool";
//		final URL url = new URL("http://tecbar.net/demo/param" + parameters);
		
		final URL url = new URL("https://10.0.0.11:3640/rk7api/v0/xmlinterface.xml");
		
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("User-Agent", USER_AGENT);
		conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8"); 
		conn.setDoOutput(true);
		conn.setConnectTimeout(5000);
		
//		username: http_user1
//		pass: 9
// 
		// Send post request
		conn.setDoOutput(true);
//		DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
//		final String msg = "<channel>ABC</channel><title>tecbar.net demo pages</title>";
//		wr.writeBytes(msg);
		// send request
//		wr.flush();
//		// close
//		wr.close();
 
		// read response
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String str;
		while ((str = in.readLine()) != null) {
			content.append(str);
		}
		in.close();
 
		return content.toString();
	}
 
	public static void main(String[] args) {
		try {
			request instance = new request();
			String response = instance.postMessage();
			System.out.println(response);
		} catch(IOException ex) {
			// process the exception
		}
	}
}