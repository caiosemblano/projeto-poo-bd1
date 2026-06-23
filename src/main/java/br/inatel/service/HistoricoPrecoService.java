package br.inatel.service;

import br.inatel.dao.HistoricoPrecoDAO;
import br.inatel.model.HistoricoPreco;

import java.time.LocalDate;
import java.util.List;

public class HistoricoPrecoService {

    private final HistoricoPrecoDAO historicoDAO;

    public HistoricoPrecoService(HistoricoPrecoDAO historicoDAO) {
        this.historicoDAO = historicoDAO;
    }

    public void inserir(HistoricoPreco historico) {
        try {
            historicoDAO.inserir(historico);
            System.out.println("Histórico cadastrado com sucesso! ID gerado: " + historico.getIdHistorico());
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar histórico: " + e.getMessage());
        }
    }

    public HistoricoPreco buscarPorId(Integer id) {
        HistoricoPreco historico = historicoDAO.buscarPorId(id);
        if (historico == null) {
            System.out.println("Histórico não encontrado!");
        }
        return historico;
    }

    public List<HistoricoPreco> listarTodos() {
        return historicoDAO.listarTodos();
    }

    public List<HistoricoPreco> buscarPorData(LocalDate data) {
        return historicoDAO.buscarPorData(data);
    }

    public void atualizar(HistoricoPreco historico) {
        try {
            historicoDAO.atualizar(historico);
            System.out.println("Histórico atualizado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar histórico: " + e.getMessage());
        }
    }

    public void deletar(Integer id) {
        try {
            historicoDAO.deletar(id);
            System.out.println("Histórico deletado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao deletar histórico: " + e.getMessage());
        }
    }
}
