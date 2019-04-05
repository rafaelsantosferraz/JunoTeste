package rafaelsantosferraz.com.base.di.module

import android.app.Application
import com.google.gson.Gson
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import rafaelsantosferraz.com.base.utils.network.ErrorInterceptor
import rafaelsantosferraz.com.base.presentation.network.GithubApi
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {


    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return logInterceptor
    }

    @Provides
    @Singleton
    fun provideNetworkConnectionInterceptor(application: Application): ErrorInterceptor {
        val networkConnectionInterceptor = ErrorInterceptor(application)
        return networkConnectionInterceptor
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor, errorInterceptor: ErrorInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(errorInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()
    }

    @Provides
    @Singleton
    fun providesGithubApi(okHttpClient: OkHttpClient, gson: Gson): GithubApi {
        return Retrofit.Builder()
            .baseUrl(GithubApi.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build()
                .create(GithubApi::class.java)
    }
}