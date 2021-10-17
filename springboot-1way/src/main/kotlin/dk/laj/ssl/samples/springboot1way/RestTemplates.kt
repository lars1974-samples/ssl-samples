package dk.laj.ssl.samples.springboot1way

import org.apache.http.client.HttpClient
import org.apache.http.conn.ssl.NoopHostnameVerifier
import org.apache.http.conn.ssl.SSLConnectionSocketFactory
import org.apache.http.conn.ssl.TrustSelfSignedStrategy
import org.apache.http.impl.client.HttpClients
import org.apache.http.ssl.SSLContextBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.core.io.ClassPathResource
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestTemplate
import java.security.KeyStore

@Configuration
class RestTemplates(
    @Autowired val env: Environment
) {

    fun get0way(): RestTemplate {
        return RestTemplate()
    }

    fun get2way(): RestTemplate {
        val keyStore = KeyStore.getInstance("pkcs12")
        keyStore.load(ClassPathResource(env.getProperty("dependencies.keystore", "")).inputStream, "password".toCharArray())
        val socketFactory = SSLConnectionSocketFactory(
            SSLContextBuilder().loadTrustMaterial(null, TrustSelfSignedStrategy()).loadKeyMaterial(keyStore, "password".toCharArray()).build(),
            NoopHostnameVerifier.INSTANCE
        )
        val httpClient: HttpClient = HttpClients.custom().setSSLSocketFactory(socketFactory).build()
        return RestTemplate(HttpComponentsClientHttpRequestFactory(httpClient))
    }
}
