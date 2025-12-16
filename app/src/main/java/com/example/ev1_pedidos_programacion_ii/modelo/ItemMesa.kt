package com.example.ev1_pedidos_programacion_ii.modelo

/**
 * Clase que representa un ítem solicitado por una mesa.
 * Contiene el platillo asociado (ItemMenu) y la cantidad.
 */
class ItemMesa(val itemMenu: ItemMenu, var cantidad: Int) {

    /**
     * Calcula el subtotal para este ítem: cantidad * precio unitario.
     */
    fun calcularSubtotal(): Int {
        return cantidad * itemMenu.valoUnitario
    }
}