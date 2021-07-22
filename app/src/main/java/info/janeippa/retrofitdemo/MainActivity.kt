package info.janeippa.retrofitdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {

    private val httpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

    private val moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    private val retrofit =
        Retrofit.Builder()
            .baseUrl("https://qiita.com")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(httpClient)
            .build()

    private val api =
        retrofit.create(QiitaApi::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialize()
    }

    private fun initialize() {
        initLayout()
    }

    private fun initLayout() {
        initClick()
    }

    private fun initClick() {
        findViewById<Button>(R.id.button1).setOnClickListener {
            loadData()
        }
        findViewById<Button>(R.id.button2).setOnClickListener {
            loadData(2)
        }
        findViewById<Button>(R.id.button3).setOnClickListener {
            loadData(3)
        }
    }

    private fun loadData(page: Int = 1) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val list = api.items(page, "Android")
            } catch(e: Exception) {
                e.printStackTrace()
            }
        }
    }
}