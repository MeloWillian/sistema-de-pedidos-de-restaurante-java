package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReceitaFederal {
    public static final double TAXA = 0.05;
    private double totalImpostoDevido;
    private List<Pedido> pedidos;

    public ReceitaFederal() {
        totalImpostoDevido = 0;
        pedidos = new ArrayList<>();
    }


    public void notificarPedido(Pedido pedido) {
        if (!pedido.isTaxaPaga()) {
            double impostoPedido = pedido.calcularValorTotal() * TAXA;
            totalImpostoDevido += impostoPedido;
            pedido.setTaxaPaga(true);
            pedidos.add(pedido);
        }
    }

    public double getTotalImpostoDevido() {
        return totalImpostoDevido;
    }

    public List<Pedido> getPedidos() {
        return Collections.unmodifiableList(pedidos);
    }
}