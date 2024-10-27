package com.example.currency_converter

import android.app.Dialog
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {

    companion object {
        private const val FROM = "from"
        private const val TO = "to"
    }

    private lateinit var convertFrom: TextView
    private lateinit var convertTo: TextView
    private lateinit var result: TextView
    private lateinit var validator: TextView
    private lateinit var amount: EditText
    private lateinit var convertBtn: Button
    private lateinit var cardView: CardView
    private lateinit var loading: FrameLayout
    private val listCurrencies: MutableList<String> = ArrayList()
    private var convertFromText: String? = null
    private var convertToText: String? = null
    private lateinit var queue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // views
        convertFrom = findViewById(R.id.convert_from_dropdown_menu)
        convertTo = findViewById(R.id.convert_to_dropdown_menu)
        result = findViewById(R.id.result_convert_text)
        amount = findViewById(R.id.amount_convert_edit_text)
        convertBtn = findViewById(R.id.convert_button)
        validator = findViewById(R.id.validator)
        cardView = findViewById(R.id.card_view)
        loading = findViewById(R.id.progress_bar_container)

        // Initialize Volley RequestQueue
        queue = Volley.newRequestQueue(this)

        // Fetch currencies list
        CurrencyService.fetchCurrencies(queue, listCurrencies, cardView, loading, this)

        // Dropdown listeners
        listCurrenciesDropdownOnclickListener(convertFrom, FROM)
        listCurrenciesDropdownOnclickListener(convertTo, TO)

        // Button CONVERT onClick
        convertBtn.setOnClickListener {
            // Validate selected currencies
            var isValid = true
            if (convertFromText.isNullOrBlank()) {
                isValid = false
                convertFrom.setBackgroundResource(R.drawable.convert_invalid_text_view)
                convertFrom.setTextColor(Color.parseColor("#FF0000"))
                convertFrom.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_drop_down_error, 0)
            }

            if (convertToText.isNullOrBlank()) {
                isValid = false
                convertTo.setBackgroundResource(R.drawable.convert_invalid_text_view)
                convertTo.setTextColor(Color.parseColor("#FF0000"))
                convertTo.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_drop_down_error, 0)
            }

            if (isValid) {
                try {
                    val amountConvert = amount.text.toString().toDouble()
                    CurrencyService.getConvertResult(
                        queue,
                        convertFromText!!,
                        convertToText!!,
                        amountConvert,
                        result,
                        cardView,
                        loading,
                        this
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                    result.setText(R.string.resultHint)
                    result.setTextColor(Color.parseColor("#AAABAE"))
                }
            } else {
                validator.visibility = View.VISIBLE
            }
        }
    }

    private fun listCurrenciesDropdownOnclickListener(textView: TextView, type: String) {
        val displayMetrics = Resources.getSystem().displayMetrics

        val width = (displayMetrics.widthPixels * 0.9).toInt()
        val height = (displayMetrics.heightPixels * 0.8).toInt()

        textView.setOnClickListener {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.currencies_dialog)
            dialog.window?.setLayout(width, height)
            dialog.show()

            val editText = dialog.findViewById<EditText>(R.id.edit_text)
            val listView = dialog.findViewById<ListView>(R.id.list_view)

            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                listCurrencies
            )
            listView.adapter = adapter

            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    adapter.filter.filter(s)
                }

                override fun afterTextChanged(s: Editable?) {}
            })

            listView.setOnItemClickListener { _, _, position, _ ->
                textView.text = adapter.getItem(position)
                dialog.dismiss()
                if (validator.visibility == View.VISIBLE) validator.visibility = View.GONE
                if (type == FROM) {
                    convertFromText = adapter.getItem(position)
                    convertFrom.setBackgroundResource(R.drawable.convert_text_view)
                    convertFrom.setTextColor(Color.parseColor("#000000"))
                    convertFrom.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_drop_down_default, 0)
                } else {
                    convertToText = adapter.getItem(position)
                    convertTo.setBackgroundResource(R.drawable.convert_text_view)
                    convertTo.setTextColor(Color.parseColor("#000000"))
                    convertTo.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_drop_down_default, 0)
                }
            }
        }
    }
}
