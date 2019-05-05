package httpConector;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConnectorUtils {

    public static void printResponse(HttpResponse response) {

        System.out.println("*****************");
        System.out.println("*Status line: " + response.getStatusLine());
        System.out.println("*-----HEADER-----");
        for (Header header : response.getAllHeaders()) {
            System.out.println("* " + header);
        }
        System.out.println("*----------------");
        String line;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String inputLine;
            StringBuilder content = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            System.out.println("* " + content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("*****************");

    }

}
