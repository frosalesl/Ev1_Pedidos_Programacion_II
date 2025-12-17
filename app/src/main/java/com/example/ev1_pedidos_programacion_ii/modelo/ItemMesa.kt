package com.example.ev1_pedidos_programacion_ii.modelo

//Se crea una clase para una mesa específica. Ella contiene el plato asociado y la cantidad.
class ItemMesa(val itemMenu: ItemMenu, var cantidad: Int) {
    //Se realiza una función que calcula el subtotal. Ello mediante la cantidad multiplicado por el precio unitario.
    fun calcularSubtotal(): Int {
        return cantidad * itemMenu.valorUnitario
    }
}