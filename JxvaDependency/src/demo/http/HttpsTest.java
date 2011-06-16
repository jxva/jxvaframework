package demo.http;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

/**
 * 用HttpsURLConnection访问HTTPS资源
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-28 16:25:00 by Jxva
 */
public class HttpsTest {
 
    private String url = "https://esales.the9.com/esa/DealerLogin.php?txt_sLogin=andysmile234&pas_sPwd=343211&userstatus=1";
    private myX509TrustManager xtm = new myX509TrustManager();
    private myHostnameVerifier hnv = new myHostnameVerifier();
    public HttpsTest() {
       SSLContext sslContext = null;
      try {
         sslContext = SSLContext.getInstance("TLS");
      X509TrustManager[] xtmArray = new X509TrustManager[] { xtm };
      sslContext.init( null,
                          xtmArray,
                          new java.security.SecureRandom() );
      } catch( GeneralSecurityException gse ) {
      }
      if( sslContext != null ) {
         HttpsURLConnection.setDefaultSSLSocketFactory(
                     sslContext.getSocketFactory() );
      }
      HttpsURLConnection.setDefaultHostnameVerifier( hnv );
    }
    public void run() {
      try {

            URLConnection urlCon = (new URL(url)).openConnection();
           
            BufferedReader in = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
            String line;
            while ((line = in.readLine()) != null)
            {
                System.out.println(line);
            }

           //  增加自己的代码
        } catch( MalformedURLException mue ) {
            mue.printStackTrace();
        } catch( IOException ioe ) {
            ioe.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        HttpsTest httpsTest = new HttpsTest();
        httpsTest.run();
    }
}
class myX509TrustManager implements X509TrustManager{
  public myX509TrustManager(){}
  public void checkClientTrusted(X509Certificate[] chain,  String authType) {}
     public void checkServerTrusted(X509Certificate[] chain,String authType) {
      System.out.println("cert: " + chain[0].toString() + ", authType: " + authType);
     }
     public X509Certificate[] getAcceptedIssuers() {
      return null;
     }
}
class myHostnameVerifier implements HostnameVerifier{
 public myHostnameVerifier(){}
  public boolean verify(String hostname,SSLSession session) {
   System.out.println("hostname: " + hostname);
   return true;
  }
}

 