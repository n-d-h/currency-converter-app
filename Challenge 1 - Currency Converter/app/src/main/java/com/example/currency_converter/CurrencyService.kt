package com.example.currency_converter

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import org.json.JSONException
import org.json.JSONObject
import java.util.*

object CurrencyService {

    private const val API_KEY = "8fdccfe36d820c9d309eacbba033ea1d"

    fun fetchCurrencies(
        queue: RequestQueue,
        listCurrencies: MutableList<String>,
        cardView: CardView,
        loading: FrameLayout,
        context: Context
    ) {
        val url = "https://data.fixer.io/api/symbols?access_key=$API_KEY"
        cardView.setCardBackgroundColor(Color.parseColor("#AAABAE"))
        loading.visibility = View.VISIBLE

        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                try {
                    val json = JSONObject(response)
                    val symbols = json.getJSONObject("symbols")

                    val keys = symbols.keys()
                    while (keys.hasNext()) {
                        listCurrencies.add(keys.next())
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                } finally {
                    cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
                    loading.visibility = View.GONE
                }
            },
            {
                cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
                loading.visibility = View.GONE
                showErrorDialog(context)
            })

        queue.add(stringRequest)
    }

    fun getConvertResult(
        queue: RequestQueue,
        from: String,
        to: String,
        amount: Double,
        result: TextView,
        cardView: CardView,
        loading: FrameLayout,
        context: Context
    ) {
        val url = "https://hexarate.paikama.co/api/rates/latest/$from?target=$to"
        cardView.setCardBackgroundColor(Color.parseColor("#AAABAE"))
        loading.visibility = View.VISIBLE

        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                try {
                    val json = JSONObject(response)
                    val rateValue = json.getJSONObject("data").getDouble("mid")
                    val resultText = String.format(Locale.getDefault(), "%,.2f", rateValue * amount)
                    result.text = resultText
                    result.setTextColor(Color.parseColor("#FF0000"))
                } catch (e: JSONException) {
                    e.printStackTrace()
                    result.setText(R.string.error_fetching_data)
                } finally {
                    cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
                    loading.visibility = View.GONE
                }
            },
            {
                cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
                loading.visibility = View.GONE
                showErrorDialog(context)
            })

        queue.add(stringRequest)
    }

    private fun showErrorDialog(context: Context) {
        AlertDialog.Builder(context)
            .setTitle("Network Error")
            .setMessage("There was an issue fetching data. Please check your connection and try again.")
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }
}
