package httpConector;


import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnectorTest {

    public static void main(String args[]) {

        postConnection("http://127.0.0.1:8080/user/create","application/json","{\"username\": \"bob\", \"password\": \"pass\" , \"userRole\": \"user\"}");
        postConnection("http://127.0.0.1:8080/user/getUser","html/text","bob");
        postConnection("http://127.0.0.1:8080/user/updateRole","application/json","{\"username\": \"bob\", \"password\": \"pass\" , \"userRole\": \"administrator\"}");
        postConnection("http://127.0.0.1:8080/user/getUser","html/text","bob");
        postConnection("http://127.0.0.1:8080/user/delete","html/text","bob");
        postConnection("http://127.0.0.1:8080/user/getUser","html/text","bob");

    }
    public static void postConnection(String url, String contentType, String urlParameters ){
        try {

            URL obj = new URL(url);
            //TODO change to HttpsURLConection when there Certificate on the server
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            //add reuqest header
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "test");
            con.setRequestProperty("Accept-Language", "de-DE");
            con.setRequestProperty("Content-Type", contentType);

            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Post parameters : " + urlParameters);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            //print result
            System.out.println(response.toString());
        } catch (IOException e) {

        }
    }
}

