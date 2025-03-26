package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Restaurante {
    private ReceitaFederal receitaFederal;
    private List<Item> cardapio;

    public Restaurante() {
        receitaFederal = new ReceitaFederal();
        cardapio = new ArrayList<>();
        cardapio.add(new Item("PFzão", 25.00));
        cardapio.add(new Item("Macarrão Carbonara", 22.0));
        cardapio.add(new Item("Coca 2l", 10.0));
        cardapio.add(new Item("Carne de Sol na Nata", 35.00));
        cardapio.add(new Item("Macarrão com linguiça", 17.0));
        cardapio.add(new Item("feijoada", 30.0));
        cardapio.add(new Item("Guarana 1l", 5.0));
        cardapio.add(new Item("Sprite 1l", 5.0));
    }

    public void realizarPedido(Cliente cliente, List<Item> itens, String formaPagamento, double desconto) {
        Pedido pedido = new Pedido(cliente);
        for (Item item : itens) {
            pedido.adicionarItem(item);
        }
        pedido.setFormaPagamento(formaPagamento);
        pedido.setDesconto(desconto);

        receitaFederal.notificarPedido(pedido);
        System.out.println("Pedido realizado para " + cliente.getNome() + ". Valor: R$ " + pedido.calcularValorTotal() + " TAXA: R$ " + (pedido.calcularValorTotalComTaxa() - pedido.calcularValorTotal()) + " ValorTotal: R$ " + pedido.calcularValorTotalComTaxa());
    }

    public List<Item> getCardapio() {
        return this.cardapio;
    }

    public double consultarImpostoDevido() {
        return receitaFederal.getTotalImpostoDevido();
    }

    public List<Pedido> getPedidos() {
        return receitaFederal.getPedidos();
    }

    public static void main(String[] args) {
        Restaurante restaurante = new Restaurante();
        Scanner scanner = new Scanner(System.in);
        Cliente clienteAtual = null;

        while (true) {
            if (clienteAtual == null) {
                clienteAtual = cadastrarNovoCliente(scanner);
            }

            System.out.println("\n=============== CARDÁPIO ===============");
            for (int i = 0; i < restaurante.getCardapio().size(); i++) {
                System.out.printf("%d - %-25s R$ %.2f\n", i, restaurante.getCardapio().get(i).getNome(), restaurante.getCardapio().get(i).getPreco());
            }
            System.out.println("=======================================");

            List<Item> itensPedido = new ArrayList<>();
            System.out.print("Quais itens você deseja, " + clienteAtual.getNome() + "? (Digite o número e 'fim' para encerrar): ");
            while (true) {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("fim")) {
                    break;
                }
                try {
                    int indice = Integer.parseInt(input);
                    if (indice >= 0 && indice < restaurante.getCardapio().size()) {
                        itensPedido.add(restaurante.getCardapio().get(indice));
                        System.out.print(restaurante.getCardapio().get(indice).getNome() + " adicionado.  Mais algum item? (número ou 'fim'): ");
                    } else {
                        System.out.print("Índice inválido. Tente novamente: ");
                    }
                } catch (NumberFormatException e) {
                    System.out.print("Entrada inválida. Digite um número ou 'fim': ");
                }
            }

            System.out.print("Forma de pagamento: ");
            String formaPagamento = scanner.nextLine();

            System.out.print("Desconto (em R$): ");
            double desconto = 0;
            while (true) {
                String input = scanner.nextLine();

                try {
                    desconto = Double.parseDouble(input);
                    if (desconto < 0) {
                        System.out.print("Desconto inválido. Digite um valor não negativo: ");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.print("Entrada inválida. Digite um número: ");
                }
            }
            restaurante.realizarPedido(clienteAtual, itensPedido, formaPagamento, desconto);

            System.out.println("\n========== AUDITORIA DE PEDIDOS ==========");
            List<Pedido> todosPedidos = restaurante.getPedidos();
            if (todosPedidos.isEmpty()) {
                System.out.println("Nenhum pedido registrado.");
            } else {
                for (Pedido p : todosPedidos) {
                    System.out.println("Cliente: " + p.getCliente().getNome());
                    System.out.print("Itens: ");
                    for (Item i : p.getItens()) {
                        System.out.print(i.getNome() + ", ");
                    }
                    System.out.println("\nForma de Pagamento: " + p.getFormaPagamento());
                    System.out.printf("Valor (com taxa, após desconto): R$ %.2f\n", p.calcularValorTotalComTaxa());
                    System.out.println("Taxa paga: " + (p.isTaxaPaga() ? "Sim" : "Não"));
                    System.out.println("---------------------------------------");
                }
            }

            System.out.print("\nDeseja fazer outro pedido? (s/n): ");
            String novoPedido = scanner.nextLine();
            if (novoPedido.equalsIgnoreCase("n")) {
                System.out.print("\nDigite s para deslogar e encerrar, e n para deslogar e realizar novo cadastro? (s/n): ");
                String novoPedido2 = scanner.nextLine();
                if(novoPedido2.equalsIgnoreCase("s")){
                    break;
                }else if (novoPedido2.equalsIgnoreCase("n")){
                    clienteAtual = cadastrarNovoCliente(scanner);
                }
                else {
                    System.out.println("Opção inválida saindo...");
                    break;
                }
            } else if (novoPedido.equalsIgnoreCase("s")){
                System.out.println("\n\n======== NOVO PEDIDO ========");
                System.out.println("Usando o mesmo cadastro (" + clienteAtual.getNome() + ")");
                continue;
            }
            else {
                System.out.println("Opção inválida saindo...");
                break;
            }
        }
        scanner.close();
    }

    private static Cliente cadastrarNovoCliente(Scanner scanner) {
        System.out.println("\n========= CADASTRO DE CLIENTE =========");
        System.out.print("Nome completo: ");
        String nome = scanner.nextLine();
        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.println("=======================================");

        return new Cliente(nome, endereco, telefone);
    }
}