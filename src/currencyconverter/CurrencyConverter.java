
package currencyconverter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Scanner;
import org.json.JSONObject;
/**
 *
 * @author rinku
 */
public class CurrencyConverter {

    /**
     * @param args the command line arguments
     * @throws java.net.ProtocolException
     */
    private static int checkCurrencyCodes(HashMap<Integer, String> currencyCodes, int key) {
        Scanner sc = new Scanner(System.in);
        while(!currencyCodes.containsKey(key)) {
            System.out.println("Invalid Currency!!");
            System.out.println("1. USD\t 2. CAD\t 3. EUR\t 4. HKD\t 5. INR");
            key = sc.nextInt();
        }
        return key;
    }
    public static void main(String[] args) throws ProtocolException, IOException {
        String toCode, fromCode;
        double amount;
        
        HashMap<Integer, String> currencyCodes = new HashMap<>(); 
        
        currencyCodes.put(1, "USD");
        currencyCodes.put(2, "CAD");
        currencyCodes.put(3, "EUR");
        currencyCodes.put(4, "HKD");
        currencyCodes.put(5, "INR");
        
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Welcome to Currency Converter!!");
        
        System.out.println("Currency converting FROM??");
        System.out.println("1. USD\t 2. CAD\t 3. EUR\t 4. HKD\t 5. INR");
        int from = sc.nextInt();
        fromCode = currencyCodes.get(checkCurrencyCodes(currencyCodes, from));
        
        System.out.println("Currency converting TO??");
        System.out.println("1. USD\t 2. CAD\t 3. EUR\t 4. HKD\t 5. INR");
        int to = sc.nextInt();
        toCode = currencyCodes.get(checkCurrencyCodes(currencyCodes, to));
        
        System.out.println("amount you want to convert?");
        amount = sc.nextDouble();
        
        sendHttpGETRequest(fromCode, toCode, amount);
        
        
        System.out.println("Thanks for using currency converter!!");
    }
    private static void sendHttpGETRequest(String fromCode, String toCode, double amount) throws MalformedURLException, ProtocolException, IOException {
        String api_key = "fca_live_fLWtfQEbvTkiaUAHKfrQjNj3Yw8o4qd0zbpikSMO";
        String api = "https://api.freecurrencyapi.com/v1/latest?apikey="+api_key+"&base_currency="+toCode;
        URL url = new URL(api);
        HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        int responseCode = httpURLConnection.getResponseCode();
        
        if(responseCode == HttpURLConnection.HTTP_OK) {
            StringBuilder response;
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String inputLine;
                response = new StringBuilder();
                while((inputLine = in.readLine())  != null) {
                    response.append(inputLine);
                }in.close();
                
            JSONObject obj = new JSONObject(response.toString());
            double exchangeRate = obj.getJSONObject("data").getDouble(fromCode);
            DecimalFormat f = new DecimalFormat("00.00");
            System.out.println();
            System.out.println(f.format(amount) + fromCode + " = " + f.format(amount/exchangeRate) + toCode);
        } else {
            System.out.println("Http request failed!!");
        }
    }
    
    
}
