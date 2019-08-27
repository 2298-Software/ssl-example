package com._2298software.com;

import java.io.OutputStream;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class SSLClientExample {
    final private static String keystoreFile = "ssl-client/src/main/resources/client-keystore.jks";
    final private static String keystorePassword = "password";  //The Password
    final private static String remoteHostname = "localhost";
    final private static int remotePort = 12345;
    final private static boolean debug = false;

    public static void main(String[] args) throws Exception {

        System.setProperty("javax.net.ssl.trustStore", keystoreFile);
        System.setProperty("javax.net.ssl.trustStorePassword", keystorePassword);
        if(debug) System.setProperty("javax.net.debug","all");
         
        SSLSocketFactory ssf = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket sslSocket = (SSLSocket) ssf.createSocket(remoteHostname, remotePort);

        OutputStream os = sslSocket.getOutputStream();
        os.write("Message from SSL Client".getBytes()); 
        os.flush();
        sslSocket.close();
    }
}
