package rafaelsantosferraz.com.base.utils.network

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import okhttp3.Interceptor
import okhttp3.Response
import java.net.ConnectException
import javax.inject.Inject

class ErrorInterceptor @Inject constructor(val application: Application): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response{
        val request = chain.request()

        // Before Request
        val isConnected = (application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo?.isConnected ?: false
        if (!isConnected){
            throw ConnectException("No connectivity")
        }


        // After Response
        val response = chain.proceed(request)

        return response
    }
}