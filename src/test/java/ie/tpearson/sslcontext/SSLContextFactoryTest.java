package ie.tpearson.sslcontext;

import nl.altindag.ssl.util.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SSLContextFactoryTest {

    @Test
    void buildSSLContext() throws IOException {
        SSLContext sslContext = SSLContextFactory.buildSSLContext();
        assertNotNull(sslContext);
        LoggerFactory.getLogger(getClass()).info("Loaded SSL Context successfully!");

        //Then.. try to connect to something?
        CloseableHttpClient client = HttpClients.custom().setSSLContext(sslContext).build();
        CloseableHttpResponse response = client.execute(new HttpGet("https://prod.idrix.eu/secure/"));
        int rsStatus = response.getStatusLine().getStatusCode();
        assertTrue(rsStatus >= 200 && rsStatus < 400);

        String responseBody = IOUtils.getContent(response.getEntity().getContent());
        assertFalse(responseBody.contains("No SSL client certificate presented"));
    }
}