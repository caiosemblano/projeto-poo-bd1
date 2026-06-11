package br.inatel.service;

import br.inatel.dao.InvestidorDAO;
import br.inatel.model.Investidor;

import java.util.List;

public class InvestidorService {

    private final InvestidorDAO investidorDAO;

    public InvestidorService(InvestidorDAO investidorDAO) {
        this.investidorDAO = investidorDAO;
    }

    public void inserir(Investidor investidor) {
        investidorDAO.inserir(investidor);
    }

    public Investidor buscarPorId(Integer id) {
        return investidorDAO.buscarPorId(id);
    }

    public List<Investidor> listarTodos() {
        return investidorDAO.listarTodos();
    }

    public void atualizar(Investidor investidor) {
        investidorDAO.atualizar(investidor);
    }

    public void deletar(Integer id) {
        investidorDAO.deletar(id);
    }
}
