keytool -genkeypair -alias 0way -keyalg RSA -keysize 2048 -storetype PKCS12 \
-dname "CN=0way, C=DK" -storepass password \
-keystore 0way.p12 -validity 3650 -ext SAN=dns:localhost,ip:127.0.0.1

keytool -genkeypair -alias 1way -keyalg RSA -keysize 2048 -storetype PKCS12 \
-dname "CN=1way, C=DK" -storepass password \
-keystore 1way.p12 -validity 3650 -ext SAN=dns:localhost,ip:127.0.0.1

keytool -genkeypair -alias 2way -keyalg RSA -keysize 2048 -storetype PKCS12 \
-dname "CN=2way, C=DK" -storepass password \
-keystore 2way.p12 -validity 3650 -ext SAN=dns:localhost,ip:127.0.0.1

keytool -genkeypair -alias browser -keyalg RSA -keysize 2048 -storetype PKCS12 \
-dname "CN=browser, C=DK" -storepass password \
-keystore browser.p12 -validity 3650 -ext SAN=dns:localhost,ip:127.0.0.1

keytool -export -alias 0way -file 0way-public.crt -keystore 0way.p12 -storepass password
keytool -export -alias 1way -file 1way-public.crt -keystore 1way.p12 -storepass password
keytool -export -alias browser -file browser-public.crt -keystore browser.p12 -storepass password

#the truststore must be JKS it seams (spring boot?)
keytool -import -alias 0way-public -file 0way-public.crt -keystore 2way-trust.p12 -storepass password -noprompt -storetype PKCS12
keytool -import -alias 1way-public -file 1way-public.crt -keystore 2way-trust.p12 -storepass password -noprompt -storetype PKCS12
keytool -import -alias browser-public -file browser-public.crt -keystore 2way-trust.p12 -storepass password -noprompt -storetype PKCS12

mv ./0way.p12 ./springboot-0way/src/main/resources/0way.p12 -f
mv ./1way.p12 ./springboot-1way/src/main/resources/1way.p12 -f
mv ./2way.p12 ./springboot-2way/src/main/resources/2way.p12 -f
mv ./2way-trust.p12 ./springboot-2way/src/main/resources/2way-trust.p12 -f

rm *.crt