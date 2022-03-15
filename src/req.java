import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Base64;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public class req {
	

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
		
		
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		
		
		
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Authorization", authHeaderValue);
		conn.setRequestProperty("User-Agent", USER_AGENT);
		conn.setRequestProperty("Content-Type", "application/xml; charset=UTF-8"); 



		conn.setDoOutput(true);
		conn.setConnectTimeout(500);
		

		// Send post request
		conn.setDoOutput(true);

		System.out.println(" -------------------------");
		DataOutputStream wr = new DataOutputStream(conn.getOutputStream());

		final String msg = "<RK7Query>\r\n"
				+ " <RK7Command CMD=\"GetOrder\">\r\n"
				+ "  <Order guid=\"{88724AA1-D484-4281-B488-F53021D6E318}\"/>\r\n"
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


	private static void disableSslVerification() {
	    try
	    {
	        // Create a trust manager that does not validate certificate chains
	        TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
	            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
	                return null;
	            }
	            public void checkClientTrusted(X509Certificate[] certs, String authType) {
	            }
	            public void checkServerTrusted(X509Certificate[] certs, String authType) {
	            }
	        }
	        };

	        // Install the all-trusting trust manager
	        SSLContext sc = SSLContext.getInstance("SSL");
	        sc.init(null, trustAllCerts, new java.security.SecureRandom());
	        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

	        // Create all-trusting host name verifier
	        HostnameVerifier allHostsValid = new HostnameVerifier() {
	            public boolean verify(String hostname, SSLSession session) {
	                return true;
	            }
	        };

	        // Install the all-trusting host verifier
	        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    } catch (KeyManagementException e) {
	        e.printStackTrace();
	    }
	}
 
	public static void main(String[] args) throws Exception {
		try {
			
//			String username = "http_user1";
//			String password = "9";
//			String auth = username + ":" + password;
//			byte[] bytes = auth.getBytes(StandardCharsets.UTF_8); 
//			String base64Encoded = Base64.getEncoder().encodeToString(bytes);
//			System.out.println("Base64 encoded text: " + base64Encoded);
			

			disableSslVerification();
			httpPostRequest instance = new httpPostRequest();
			String response = instance.postMessage();
			System.out.println(response);
		} catch(IOException ex) {
			System.out.println("=======================" + ex);
			// process the exception
		}
	}
}