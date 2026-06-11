package br.inatel.service;

import br.inatel.dao.CarteiraDAO;
import br.inatel.model.Carteira;

import java.util.List;

public class CarteiraService {

    private final CarteiraDAO carteiraDAO;

    public CarteiraService(CarteiraDAO carteiraDAO) {
        this.carteiraDAO = carteiraDAO;
    }

    public void inserir(Carteira carteira) {
        try {
            carteiraDAO.inserir(carteira);
            System.out.println("Carteira cadastrada com sucesso! ID gerado: " + carteira.getIdCarteira());
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar carteira. Verifique se o ID do Investidor realmente existe.");
        }
    }

    public Carteira buscarPorId(Integer id) {
        Carteira carteira = carteiraDAO.buscarPorId(id);
        if (carteira == null) {
            System.out.println("Carteira não encontrada!");
        }
        return carteira;
    }

    public Carteira buscarPorNome(String nome) {
        Carteira carteira = carteiraDAO.buscarPorNome(nome);
        if (carteira != null) {
            System.out.println("\nCarteira Encontrada:");
            System.out.println("ID: " + carteira.getIdCarteira());
            System.out.println("Nome: " + carteira.getNomeCarteira());
            System.out.println("Descrição: " + (carteira.getDescricao() != null ? carteira.getDescricao() : ""));
            System.out.println("Valor Total Investido: " + (carteira.getValorTotalInvestido() != null ? "R$ " + carteira.getValorTotalInvestido() : "R$ 0.00"));
            System.out.println("Data de Criação: " + carteira.getDataCriacao());
        } else {
            System.out.println("Carteira não encontrada.");
        }
        return carteira;
    }

    public List<Carteira> listarTodos() {
        System.out.println("\n--- Lista de Carteiras Cadastradas ---");
        List<Carteira> carteiras = carteiraDAO.listarTodos();

        if (carteiras.isEmpty()) {
            System.out.println("Nenhuma carteira cadastrada.");
            return carteiras;
        }

        System.out.printf("%-5s | %-25s | %-18s | %-15s | %-35s\n",
                "ID", "Nome", "Valor Investido", "Data Criação", "Descrição");
        System.out.println("---------------------------------------------------------------------------------------------------------");
        for (Carteira carteira : carteiras) {
            String valor = carteira.getValorTotalInvestido() != null ? "R$ " + carteira.getValorTotalInvestido().toString() : "R$ 0.00";
            System.out.printf("%-5d | %-25s | %-18s | %-15s | %-35s\n",
                    carteira.getIdCarteira(),
                    carteira.getNomeCarteira(),
                    valor,
                    carteira.getDataCriacao(),
                    carteira.getDescricao() != null ? carteira.getDescricao() : "");
        }
        return carteiras;
    }

    public void atualizar(Carteira carteira) {
        try {
            carteiraDAO.atualizar(carteira);
            System.out.println("Carteira atualizada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar carteira: " + e.getMessage());
        }
    }

    public void deletar(Integer id) {
        try {
            carteiraDAO.deletar(id);
            System.out.println("Carteira deletada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao deletar carteira: " + e.getMessage());
        }
    }
}
