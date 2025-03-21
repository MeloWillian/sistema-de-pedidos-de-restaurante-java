package org.example;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private Cliente cliente;
    private List<Item> itens;
    private double desconto;
    private String formaPagamento;
    private boolean taxaPaga;


    public Pedido(Cliente cliente) {
        this.cliente = cliente;
        this.itens = new ArrayList<>();
        this.desconto = 0;
        this.taxaPaga = false;
    }

    public void adicionarItem(Item item) {
        itens.add(item);
    }

    public double calcularValorTotal() {
        double valorTotal = 0;
        for (Item item : itens) {
            valorTotal += item.getPreco();
        }
        return valorTotal - desconto;
    }

    public double calcularValorTotalComTaxa() {
        double valorTotal = 0;
        for (Item item : itens) {
            valorTotal += item.getPreco();
        }
        return valorTotal - desconto + (valorTotal * ReceitaFederal.TAXA);
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }


    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public boolean isTaxaPaga() {
        return taxaPaga;
    }

    public void setTaxaPaga(boolean taxaPaga){
        this.taxaPaga = taxaPaga;
    }

    public String getFormaPagamento(){
        return this.formaPagamento;
    }

    public List<Item> getItens(){
        return this.itens;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

}