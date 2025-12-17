package com.example.ev1_pedidos_programacion_ii

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import com.example.ev1_pedidos_programacion_ii.modelo.CuentaMesa
import com.example.ev1_pedidos_programacion_ii.modelo.ItemMenu
import java.text.NumberFormat
import java.util.Locale

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var pastel: ItemMenu
    private lateinit var cazuela: ItemMenu
    private val cuentaMesa = CuentaMesa()
    private val formatoCLP = NumberFormat.getCurrencyInstance(Locale("es", "CL"))
    private lateinit var etCantidadPastel: EditText
    private lateinit var tvSubtotalPastel: TextView
    private lateinit var etCantidadCazuela: EditText
    private lateinit var tvSubtotalCazuela: TextView
    private lateinit var switchPropina: SwitchCompat
    private lateinit var tvTotalComida: TextView
    private lateinit var tvMontoPropina: TextView
    private lateinit var tvTotalFinal: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pastel = ItemMenu(getString(R.string.pastel_nombre), 12000)
        cazuela = ItemMenu(getString(R.string.cazuela_nombre), 10000)

        bindViews()
        setupListeners()
        recalculateTotals()
    }
    private fun bindViews() {
        etCantidadPastel = findViewById(R.id.et_cantidad_pastel)
        tvSubtotalPastel = findViewById(R.id.tv_subtotal_pastel)
        etCantidadCazuela = findViewById(R.id.et_cantidad_cazuela)
        tvSubtotalCazuela = findViewById(R.id.tv_subtotal_cazuela)
        switchPropina = findViewById(R.id.switch_propina)
        tvTotalComida = findViewById(R.id.tv_total_comida)
        tvMontoPropina = findViewById(R.id.tv_monto_propina)
        tvTotalFinal = findViewById(R.id.tv_total_final)
    }
    private fun setupListeners() {
        etCantidadPastel.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                updateItem(pastel, s.toString(), tvSubtotalPastel)
            }
        })

        etCantidadCazuela.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                updateItem(cazuela, s.toString(), tvSubtotalCazuela)
            }
        })

        switchPropina.setOnCheckedChangeListener { _, isChecked ->
            cuentaMesa.aceptaPropina = isChecked
            recalculateTotals()
        }
    }

    private fun updateItem(item: ItemMenu, cantidadString: String, subtotalView: TextView) {
        val cantidad = cantidadString.toIntOrNull() ?: 0
        cuentaMesa.agregarItem(item, cantidad)

        val subtotalItem = cuentaMesa.buscarItem(item)?.calcularSubtotal() ?: 0
        subtotalView.text = formatoCLP.format(subtotalItem)

        recalculateTotals()
    }

    fun recalculateTotals() {
        val totalComida = cuentaMesa.calcularTotalSinPropina()
        val montoPropina = cuentaMesa.calcularPropina()
        val totalFinal = cuentaMesa.calcularTotalConPropina()

        tvTotalComida.text = formatoCLP.format(totalComida)
        tvMontoPropina.text = formatoCLP.format(montoPropina)
        tvTotalFinal.text = formatoCLP.format(totalFinal)
    }
}