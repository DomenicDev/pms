package httpConector;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

/**
 * The RestClient provides basic methods for communication with REST Services.
 */
public class RestClient {

    private CloseableHttpClient client;
    private HttpClientContext context;
    private CookieStore cookieStore;
    private CredentialsProvider credentialsProvider = new BasicCredentialsProvider();


    /**
     * Creates a new Rest Client.
     * The constructor also sets up the most important components such as a HttpClientContext
     * and a CredentialsProvider.
     */
    public RestClient() {

        // create cookie store
        this.cookieStore = new BasicCookieStore();

        // create client context
        this.context = HttpClientContext.create();

        // create http client
        client = HttpClientBuilder.create()
                .setDefaultCookieStore(cookieStore)
                .setDefaultCredentialsProvider(credentialsProvider)
                .build();
    }

    /**
     * Specifies the credentials for authorized request
     * @param username the username used for basic authentication
     * @param password the password used for basic authentication
     */
    public void setLoginCredentials(String username, String password) {
        this.credentialsProvider.setCredentials(new AuthScope(AuthScope.ANY), new UsernamePasswordCredentials(username, password));
    }

    /**
     * Sends a HTTP-POST with the specified json object to the specified url and returns the response object.
     * @param url the url to send the post to
     * @param json the json object contained in the body of the post
     * @return the http response
     * @throws IOException if connection aborted or other communication problems occurred
     */
    public HttpResponse postJson(String url, String json) throws IOException {
        // validate input parameters
        checkForNull(url);
        checkForNull(json);

        HttpPost post = new HttpPost(url);
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-type", "application/json");

        // add json body
        post.setEntity(new StringEntity(json));

        // send post and retrieve response
        CloseableHttpResponse response = client.execute(post);
        response.close();

        // return response object
        return response;
    }

    public HttpResponse get(String url) throws IOException {
        checkForNull(url);

        HttpGet get = new HttpGet(url);
        return client.execute(get);
    }

    /**
     * This method will throw an exception if the specified object is null.
     * If the object is not null, nothing happens.
     * @param o the object to be checked for null
     */
    private void checkForNull(Object o) {
        if (o == null) {
            throw new IllegalArgumentException("null is not allowed here.");
        }
    }

    /**
     * Closes the client properly.
     */
    public void close() {
        try {
            this.client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
