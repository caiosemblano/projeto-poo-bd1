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
        return carteiraDAO.buscarPorNome(nome);
    }

    public List<Carteira> listarTodos() {
        return carteiraDAO.listarTodos();
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
