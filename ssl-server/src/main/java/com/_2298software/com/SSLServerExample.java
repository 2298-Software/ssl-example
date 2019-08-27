package com._2298software.com;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class SSLServerExample {
    final private static String keystoreFile = "ssl-server/src/main/resources/server-keystore.jks"; // The FileName
    final private static String keystorePassword = "password"; // The Password
    final private static int port = 12345;
    final private static boolean debug = false;

    public static void main(String[] args) throws Exception {
        System.setProperty("javax.net.ssl.keyStore", keystoreFile);
        System.setProperty("javax.net.ssl.keyStorePassword", keystorePassword);
        if (debug) System.setProperty("javax.net.debug", "all");

        SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        SSLServerSocket serverSocket = (SSLServerSocket) ssf.createServerSocket(port);

        String data;
        while (true) {
            SSLSocket socket = (SSLSocket) serverSocket.accept();
            InputStream is = socket.getInputStream();

            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(is));
            while ((data = bufferedreader.readLine()) != null) {
                System.out.println(data);
                System.out.flush();
            }
            socket.close();
        }
    }
}
