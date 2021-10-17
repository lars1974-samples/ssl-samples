package dk.laj.ssl.samples.springboot0way

import org.apache.http.client.HttpClient
import org.apache.http.conn.ssl.NoopHostnameVerifier
import org.apache.http.conn.ssl.SSLConnectionSocketFactory
import org.apache.http.conn.ssl.TrustSelfSignedStrategy
import org.apache.http.impl.client.HttpClients
import org.apache.http.ssl.SSLContextBuilder
import org.apache.http.ssl.SSLContexts
import org.apache.http.ssl.TrustStrategy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.core.io.ClassPathResource
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestTemplate
import java.security.KeyStore
import java.security.cert.X509Certificate

@Configuration
class RestTemplates(
    @Autowired val env: Environment
) {

    fun get1Way(): RestTemplate {
        val acceptingTrustStrategy = TrustStrategy { _: Array<X509Certificate?>?, _: String? -> true }
        val csf = SSLConnectionSocketFactory(SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build())
        val httpClient = HttpClients.custom().setSSLSocketFactory(csf).build()
        val requestFactory = HttpComponentsClientHttpRequestFactory(httpClient)
        return RestTemplate(requestFactory)
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
