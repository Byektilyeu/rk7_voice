import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


public class httpPostRequest {
 
	public String postMessage() throws IOException {
		final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) Version/7.0.3 Safari/7046A194A";
		final StringBuffer content = new StringBuffer();
//		
//		final String parameters = "?name=tecbar&group=middleschool";
//		final URL url = new URL("http://tecbar.net/demo/param" + parameters);
		
		final URL url = new URL("https://10.0.0.111:8086/rk7api/v0/xmlinterface.xml");
		

		String username = "http_user1";
		String password = "9";
		
		String auth = username + ":" + password;
		
		byte[] bytes = auth.getBytes(StandardCharsets.UTF_8); 
		String base64Encoded = Base64.getEncoder().encodeToString(bytes);
		
		String authHeaderValue = "Basic " + new String(base64Encoded);
		System.out.println("Base64 encoded text: " + base64Encoded);

		
//		byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
//		String authHeaderValue = "Basic " + new String(encodedAuth);
		
		
//		String encoding = Base64.getEncoder().encodeToString((auth).getBytes(‌));
//
//		String authHeaderValue = "Basic " + new String(encoding);
		
		
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		
		
		
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Authorization", authHeaderValue);
		conn.setRequestProperty("User-Agent", USER_AGENT);
		conn.setRequestProperty("Content-Type", "application/xml; charset=UTF-8"); 
		conn.setDoOutput(true);
		conn.setConnectTimeout(500000);
		
		
		// Send post request
		conn.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		final String msg = "<RK7Query>\r\n"
				+ " <RK7Command CMD=\"GetOrderList\" >\r\n"
				+ " </RK7Command>\r\n"
				+ "</RK7Query>";
		wr.writeBytes(msg);
//		 send request
		wr.flush();
		// close
		wr.close();
 
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
			
//			String username = "http_user1";
//			String password = "9";
//			String auth = username + ":" + password;
//			byte[] bytes = auth.getBytes(StandardCharsets.UTF_8); 
//			String base64Encoded = Base64.getEncoder().encodeToString(bytes);
//			System.out.println("Base64 encoded text: " + base64Encoded);
			
			
			httpPostRequest instance = new httpPostRequest();
			String response = instance.postMessage();
			System.out.println(response);
		} catch(IOException ex) {
			System.out.println("=======================" + ex);
			// process the exception
		}
	}
}