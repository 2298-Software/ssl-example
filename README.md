# SSL Example

This project is an example of SSL Client/Server communication. Including creating the root CA and signing certificate requests.

## Setup
The keys, certs, etc used here are only for this project.  The output of this will be a client and server keystore, each containing the needed keys and certs for communication.  The paths to these files can be set in both the client and server example.

### Generate root ca key
openssl genrsa -des3 -out rootCA.key 4096

### Generate root ca certificate
openssl req -x509 -new -nodes -key rootCA.key -sha256 -days 1024 -out rootCA.crt

### Generate server keys and cert signing request
openssl genrsa -out server.2298software.com.key 2048  
openssl req -new -key server.2298software.com.key -out server.2298software.com.csr

### Generate client keys and cert signing request
openssl genrsa -out client.2298software.com.key 2048  
openssl req -new -key client.2298software.com.key -out client.2298software.com.csr

### Sign cert requests with root key to generate certs
openssl x509 -req -in server.2298software.com.csr -CA rootCA.crt -CAkey rootCA.key -CAcreateserial -out server.2298software.com.crt -days 500 -sha256  
openssl x509 -req -in client.2298software.com.csr -CA rootCA.crt -CAkey rootCA.key -CAcreateserial -out client.2298software.com.crt -days 500 -sha256

### Export server key in p12 format for later import into keystore
openssl pkcs12 -export -name server-key -in server.2298software.com.crt -inkey server.2298software.com.key -out server.2298software.com.p12  
openssl pkcs12 -export -name client-key -in client.2298software.com.crt -inkey client.2298software.com.key -out client.2298software.com.p12


### Server side keystore work
#### Import server cert
keytool -import -alias server-cert -file server.2298software.com.crt -keystore server-keystore.jks

#### Import root CA
keytool -import -trustcacerts -alias root -file rootCA.crt -keystore server-keystore.jks

#### Import server key
keytool -importkeystore -destkeystore server-keystore.jks -srckeystore server.2298software.com.p12 -srcstoretype pkcs12 -alias server-key


### Client side keystore work
#### Import client cert
keytool -import -alias client-cert -file client.2298software.com.crt -keystore client-keystore.jks

#### Import root CA cert
keytool -import -trustcacerts -alias root -file rootCA.crt -keystore client-keystore.jks

#### Import client key
keytool -importkeystore -destkeystore client-keystore.jks -srckeystore client.2298software.com.p12 -srcstoretype pkcs12 -alias client-key

#### Utility
keytool -list -v -keystore server-keystore.jks  
keytool -list -v -keystore client-keystore.jks
