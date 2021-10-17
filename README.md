# SSL samples
There are 3 spring boot services. 
* 0way (plain http endpoint)
* 1way (plain https endpoint)
* 2way (2 way SSL client certificate must be part of the service truststore)

# Certificate
All certificate work is created with the following script create_cert.sh

to be able to call 2 way from chrome you must import browser certificate to personal certificates