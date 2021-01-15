package ie.tpearson.sslcontext;

import nl.altindag.ssl.SSLFactory;
import nl.altindag.ssl.util.PemUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.X509ExtendedKeyManager;
import javax.net.ssl.X509ExtendedTrustManager;

public class SSLContextFactory {

    public static SSLContext buildSSLContext(){
        X509ExtendedTrustManager trustedCerts = PemUtils.loadTrustMaterial( "dst-root.crt");
        X509ExtendedKeyManager key = PemUtils.loadIdentityMaterial("client.cer", "client.key");
        SSLFactory sslFactory = SSLFactory.builder().withDefaultTrustMaterial().withTrustMaterial(trustedCerts).withIdentityMaterial(key).build();

        return sslFactory.getSslContext();
    }

}
