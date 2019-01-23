package com.howshea.basemodule.web

/**
 * Created by 陶海啸
 * on 2019/1/23
 */
import java.security.SecureRandom
import java.security.cert.X509Certificate

import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object SSLSocketClient {
	//获取这个SSLSocketFactory
	val sslSocketFactory: SSLSocketFactory
		get() {
			try {
				val sslContext = SSLContext.getInstance("SSL")
				sslContext.init(null, trustManager, SecureRandom())
				return sslContext.socketFactory
			} catch (e: Exception) {
				throw RuntimeException(e)
			}

		}

	//获取TrustManager
	private val trustManager: Array<TrustManager>
		get() {
			return arrayOf(object : X509TrustManager {
				override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}

				override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}

				override fun getAcceptedIssuers(): Array<X509Certificate> {
					return arrayOf()
				}
			})
		}

	//获取HostnameVerifier
	val hostnameVerifier: HostnameVerifier
		get() {
			return HostnameVerifier { _, _ -> true }
		}
}
