package persistence.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Currency;
import model.ExchangeRate;
import persistence.ExchangeRateLoader;

public class RestExchangeRateLoader implements ExchangeRateLoader {

    @Override
    public ExchangeRate load(Currency from, Currency to) {
        System.out.println(from);
        System.out.println(to);
        //System.out.println(read(from.getCode()));
        //System.out.println(from);
        return new ExchangeRate(from, to, (readRate(read(from.getCode(), to.getCode()))));
    }

    private String read(String from, String to) {
        URL url = null;
        try {
            url = new URL("https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/" + 
                    from.toLowerCase() + "/" + to.toLowerCase() + ".json");
        } catch (MalformedURLException ex) {
            Logger.getLogger(RestExchangeRateLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        BufferedReader in = null;
        String line = null;
        try {
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            while ((line = in.readLine()) != null) {
                if (line.contains(to.toLowerCase())) break;
            }
            in.close();
        } catch (IOException ex) {
            Logger.getLogger(RestExchangeRateLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return line;
    }
    
   private Double readRate(String line) {
       return Double.parseDouble(line.split(": ")[1]);
   }

    
    
    /**
    private double read(String from, String to) throws IOException {
        //String line = read(new URL("https://api.fixer.io/lastest?bae="+from+"&symbols="+to));
        String line = read(new URL("https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/"+from.toLowerCase() + "/" + to.toLowerCase() + ".json"));
        System.out.println(line);
        System.out.println(Double.parseDouble(line.split(": ")[1]));
        return Double.parseDouble(line.split(": ")[3]); // substring(line.indexOf(to)+5, line.indexOf("}")));
    }
    
    private String read(URL url) throws IOException {
        InputStream is = url.openStream();
        byte[] buffer = new byte[1024];
        int length = is.read(buffer);
        return new String(buffer,0,length);
    }
    * 
    **/
}
