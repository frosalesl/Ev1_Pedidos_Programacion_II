package com.example.ev1_pedidos_programacion_ii.modelo

//Esta clase es la central, ya que gestiona la cuenta de la mesa y los ítems pedidos. También, realiza los cálculos finales con o sin propina.
class CuentaMesa {

    // Almacena los ítems pedidos
    private val _items: MutableList<ItemMesa> = mutableListOf()

    // Activar odesactivar la propina del 10%.
    var aceptaPropina: Boolean = false

    //Se configura una función para agregar o actualizar ítem(s) en la cuenta, donde si la cantidad es cero, el ítem de elimina de la lista.
    fun agregarItem(item: ItemMenu, cantidad: Int) {
        if (cantidad > 0) {
            val existente = _items.find { it.itemMenu.nombre == item.nombre }
            if (existente != null) {
                existente.cantidad = cantidad
            } else {
                _items.add(ItemMesa(item, cantidad))
            }
        } else {
            _items.removeAll { it.itemMenu.nombre == item.nombre }
        }
    }

    //Se configura un cálculo del monto total de los pedidos sin incluir la propina.
    fun calcularTotalSinPropina(): Int {
        return _items.sumOf { it.calcularSubtotal() }
    }

    //Se configura un cálculo para la propina del 10%, que se redondea al entero más cercano.
    fun calcularPropina(): Int {
        if (!aceptaPropina) {
            return 0
        }
        val totalComida = calcularTotalSinPropina()
        return if (totalComida > 0) {
            (totalComida * 0.10).toInt()
        } else {
            0
        }
    }

    //Se configura una función que calculta el total con propina del 10%.
    fun calcularTotalConPropina(): Int {
        return calcularTotalSinPropina() + calcularPropina()
    }

    //Se configura una función qque busca y retorna un ItemMesa por su nombre del plato para obtener el subtotal de un plato individual.
    fun buscarItem(item: ItemMenu): ItemMesa? {
        return _items.find { it.itemMenu.nombre == item.nombre }
    }
}