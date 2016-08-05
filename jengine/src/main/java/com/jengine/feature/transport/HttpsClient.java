package com.jengine.feature.transport;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;

/**
 * @author nouuid
 * @date 3/28/2016
 * @description
 */
public class HttpsClient {
    private HttpsURLConnection httpsURLConnection;

    HostnameVerifier hv = new HostnameVerifier() {
        public boolean verify(String urlHostName, SSLSession session) {
            System.out.println("Warning: URL Host: " + urlHostName + " vs. " + session.getPeerHost());
            return true;
        }
    };

    public void connect(String urlStr) {
        URL url;
        try {
            url = new URL(urlStr);
            httpsURLConnection = (HttpsURLConnection) url.openConnection();
            decorateWithTLS(httpsURLConnection);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected synchronized void decorateWithTLS(HttpsURLConnection connection) {
        javax.net.ssl.SSLContext sc = null;
        javax.net.ssl.TrustManager[] trustAllCerts = null;
        try {
            trustAllCerts = new javax.net.ssl.TrustManager[1];
            trustAllCerts[0] = new TrustAllManager();
            sc = javax.net.ssl.SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, null);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        connection.setHostnameVerifier(hv);
        connection.setSSLSocketFactory(sc.getSocketFactory());
    }

    /**
     * dumpl all cert info
     */
    public void printHttpsCert() {
        if (httpsURLConnection != null) {
            try {
                System.out.println("Response Code : " + httpsURLConnection.getResponseCode());
                System.out.println("Cipher Suite : " + httpsURLConnection.getCipherSuite());
                System.out.println("\n");

                Certificate[] certs = httpsURLConnection.getServerCertificates();
                for (Certificate cert : certs) {
                    System.out.println("Cert Type : " + cert.getType());
                    System.out.println("Cert Hash Code : " + cert.hashCode());
                    System.out.println("Cert Public Key Algorithm : "
                            + cert.getPublicKey().getAlgorithm());
                    System.out.println("Cert Public Key Format : "
                            + cert.getPublicKey().getFormat());
                    System.out.println("\n");
                }
            } catch (SSLPeerUnverifiedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * dump all the content
     */
    public void printContent() {
        if (httpsURLConnection != null) {
            try {
                System.out.println("****** Content of the URL ********");
                BufferedReader br = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
                String input;
                while ((input = br.readLine()) != null) {
                    System.out.println(input);
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
