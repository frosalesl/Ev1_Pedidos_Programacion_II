package com.example.ev1_pedidos_programacion_ii.modelo

/**
 * Clase central que gestiona la cuenta de la mesa, los ítems pedidos y los cálculos finales.
 */
class CuentaMesa {

    // Lista mutable que almacena los ítems pedidos (ItemMesa)
    private val _items: MutableList<ItemMesa> = mutableListOf()

    // Propiedad para activar/desactivar la propina (10%)
    var aceptaPropina: Boolean = false

    /**
     * Agrega o actualiza un ítem en la cuenta.
     * Si la cantidad es cero, el ítem se elimina de la lista (solución compatible con API 16).
     */
    fun agregarItem(item: ItemMenu, cantidad: Int) {
        if (cantidad > 0) {
            val existente = _items.find { it.itemMenu.nombre == item.nombre }
            if (existente != null) {
                // Si existe, actualiza la cantidad
                existente.cantidad = cantidad
            } else {
                // Si no existe, agrega el nuevo ítem
                _items.add(ItemMesa(item, cantidad))
            }
        } else {
            // Elimina el ítem si la cantidad es 0 (uso de removeAll compatible)
            _items.removeAll { it.itemMenu.nombre == item.nombre }
        }
    }

    /**
     * Calcula el monto total de los pedidos sin incluir la propina.
     */
    fun calcularTotalSinPropina(): Int {
        return _items.sumOf { it.calcularSubtotal() }
    }

    /**
     * Calcula el monto de la propina (10% del total sin propina).
     */
    fun calcularPropina(): Int {
        if (!aceptaPropina) {
            return 0
        }
        val totalComida = calcularTotalSinPropina()
        return if (totalComida > 0) {
            // Calcula el 10% y redondea al entero más cercano
            (totalComida * 0.10).toInt()
        } else {
            0
        }
    }

    /**
     * Calcula el monto total a pagar (total de comida + propina).
     */
    fun calcularTotalConPropina(): Int {
        return calcularTotalSinPropina() + calcularPropina()
    }

    /**
     * Busca y retorna un ItemMesa específico por su nombre de platillo.
     * Útil para obtener el subtotal de un plato individual en la interfaz.
     */
    fun buscarItem(item: ItemMenu): ItemMesa? {
        return _items.find { it.itemMenu.nombre == item.nombre }
    }
}