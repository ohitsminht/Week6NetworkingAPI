package edu.du.week6networkingapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import edu.du.week6networkingapi.model.CarJSONModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var service: CarService
    lateinit var gson: Gson
    lateinit var requestText: TextView
    lateinit var responseText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://my-json-server.typicode.com/ohitsminht/Week6NetworkingAPI/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(CarService::class.java)
        gson = GsonBuilder().setPrettyPrinting().create()

        requestText = findViewById(R.id.request_text)
        responseText = findViewById(R.id.response_text)

        findViewById<Button>(R.id.get_button).setOnClickListener {
            if (TextUtils.isEmpty(requestText.text)) {
                makeListCall {
                    service.getCars()
                }
            } else {
                makeObjectCall {
                    service.getCars(requestText.text.toString())
                }
            }
        }

        findViewById<Button>(R.id.post_button).setOnClickListener {
            val carObject = JSONObject()
            carObject.put("id", "3")
            carObject.put("make", "Subaru")
            carObject.put("model", "WRX")
            makeObjectCall {
                service.createCar(
                    carObject.toString().toRequestBody("application/json".toMediaTypeOrNull())
                )
            }
        }

        findViewById<Button>(R.id.put_button).setOnClickListener {
            val jsonObject = JSONObject()
            jsonObject.put("id", "2")
            jsonObject.put("model", "GR Supra")
            makeObjectCall {
                service.updateCar(
                    jsonObject.getString("id"),
                    jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())
                )
            }
        }

        findViewById<Button>(R.id.delete_button).setOnClickListener {
            makeObjectCall {
                service.deleteCar(requestText.text.toString())
            }
        }
    }

    fun makeListCall(action: suspend () -> Response<List<CarJSONModel>>) {
        CoroutineScope(Dispatchers.IO).launch {
            var response: Response<List<CarJSONModel>> = action()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    responseText.text = gson.toJson(response.body())
                } else {
                    responseText.text = response.code().toString()
                }
            }
        }
    }

    fun makeObjectCall(action: suspend () -> Response<CarJSONModel>) {
        CoroutineScope(Dispatchers.IO).launch {
            var response: Response<CarJSONModel> = action()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    responseText.text = gson.toJson(response.body())
                } else {
                    responseText.text = response.code().toString()
                }
            }
        }
    }
}