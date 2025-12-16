package com.example.ev1_pedidos_programacion_ii

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
// Importamos la versión de compatibilidad para el Switch
import androidx.appcompat.widget.SwitchCompat
// Importaciones de CLASES DE MODELO con la RUTA COMPLETA:
import com.example.ev1_pedidos_programacion_ii.modelo.CuentaMesa
import com.example.ev1_pedidos_programacion_ii.modelo.ItemMenu
import java.text.NumberFormat
import java.util.Locale

// Suprimimos la advertencia de depreciación para el constructor de Locale, necesario para API 16
@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    // 1. Inicialización de los ítems fijos del menú
    private lateinit var pastel: ItemMenu
    private lateinit var cazuela: ItemMenu

    // 2. Inicialización de la clase de cálculo central
    private val cuentaMesa = CuentaMesa()

    // 3. Formato de moneda chilena (Compatible con API 16)
    private val formatoCLP = NumberFormat.getCurrencyInstance(Locale("es", "CL"))

    // 4. Referencias a vistas
    private lateinit var etCantidadPastel: EditText
    private lateinit var tvSubtotalPastel: TextView
    private lateinit var etCantidadCazuela: EditText
    private lateinit var tvSubtotalCazuela: TextView
    private lateinit var switchPropina: SwitchCompat // <-- CORRECCIÓN: Usamos SwitchCompat
    private lateinit var tvTotalComida: TextView
    private lateinit var tvMontoPropina: TextView
    private lateinit var tvTotalFinal: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicialización de los ítems del menú con sus valores fijos
        pastel = ItemMenu(getString(R.string.pastel_nombre), 12000)
        cazuela = ItemMenu(getString(R.string.cazuela_nombre), 10000)

        // Enlace de vistas por ID
        bindViews()

        // Configuración de Listeners (Eventos)
        setupListeners()

        // Inicializar totales en cero
        recalculateTotals()
    }

    private fun bindViews() {
        etCantidadPastel = findViewById(R.id.et_cantidad_pastel)
        tvSubtotalPastel = findViewById(R.id.tv_subtotal_pastel)
        etCantidadCazuela = findViewById(R.id.et_cantidad_cazuela)
        tvSubtotalCazuela = findViewById(R.id.tv_subtotal_cazuela)
        switchPropina = findViewById(R.id.switch_propina) // findViewById sigue siendo correcto
        tvTotalComida = findViewById(R.id.tv_total_comida)
        tvMontoPropina = findViewById(R.id.tv_monto_propina)
        tvTotalFinal = findViewById(R.id.tv_total_final)
    }

    private fun setupListeners() {
        // Listener para la cantidad del Pastel
        etCantidadPastel.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                updateItem(pastel, s.toString(), tvSubtotalPastel)
            }
        })

        // Listener para la cantidad de la Cazuela
        etCantidadCazuela.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                updateItem(cazuela, s.toString(), tvSubtotalCazuela)
            }
        })

        // Listener para el Switch de Propina
        switchPropina.setOnCheckedChangeListener { _, isChecked ->
            cuentaMesa.aceptaPropina = isChecked
            recalculateTotals()
        }
    }

    private fun updateItem(item: ItemMenu, cantidadString: String, subtotalView: TextView) {
        val cantidad = cantidadString.toIntOrNull() ?: 0
        cuentaMesa.agregarItem(item, cantidad)

        // 1. Actualiza el subtotal individual
        val subtotalItem = cuentaMesa.buscarItem(item)?.calcularSubtotal() ?: 0
        subtotalView.text = formatoCLP.format(subtotalItem)

        // 2. Recalcula todos los totales de la cuenta
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