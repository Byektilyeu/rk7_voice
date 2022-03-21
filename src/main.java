import java.io.FileWriter;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

public class main {
	
	public static void main(String[] args) throws Exception {
		
		
		new Timer().scheduleAtFixedRate(new TimerTask(){
		    @Override
		    public void run(){
		    	try {
		    		mainFunction();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		},0,3000);
	}

	private static void mainFunction() throws Exception{
			try {
				sslDisable ssl = new sslDisable();
				ssl.disableSslVerification();
				
				request instance = new request();
				String response = instance.postMessage();
				
				String qmsNum = null;
				
				JSONObject jsonObj = XML.toJSONObject(response);
				JSONObject RK7QueryResult = (JSONObject) jsonObj.get("RK7QueryResult");
				JSONObject CommandResult = (JSONObject) RK7QueryResult.get("CommandResult");
				JSONObject Order = (JSONObject) CommandResult.get("Order");
				JSONObject ExternalProps = (JSONObject) Order.get("ExternalProps");
				
				JSONArray arrProp = (JSONArray) ExternalProps.getJSONArray("Prop");
				for(int i = 0; i < arrProp.length(); i ++) {
					JSONObject Prop = (JSONObject) arrProp.getJSONObject(i);
					String qmsNumber = (String) Prop.get("value");  
					if(qmsNumber.length() == 3) {
						qmsNum = qmsNumber;
					}
				}
				
				String kdsstate = (String) Order.get("kdsstate");  
	            int visit = (int) Order.get("visit");         

	            System.out.println("GETORDER XML DATA: " + response);
	            System.out.println("GETORDER JSON DATA: " + jsonObj);
	            System.out.println("visit: " + visit);
	            System.out.println("kdsstate: " + kdsstate);
				System.out.println("qmsNumber: " + qmsNum);
				
				 FileWriter fw=new FileWriter("C:\\Users\\Lenovo T470\\Desktop\\RK7Voice\\test.txt", true);    
				 
				 fw.write(kdsstate+"/"); 
				 fw.write(String.valueOf(visit)+"/"); 
				 fw.write(qmsNum+"/"); 
				 fw.write(System.lineSeparator());
				 fw.close();     
		           
			} catch(IOException ex) {
				System.out.println("=======================" + ex);
			}
		}
}
