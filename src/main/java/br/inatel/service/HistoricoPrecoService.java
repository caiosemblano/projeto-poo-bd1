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

    public void inserir(HistoricoPreco historico) throws Exception {
        historicoDAO.inserir(historico);
    }

    public HistoricoPreco buscarPorId(Integer id) {
        return historicoDAO.buscarPorId(id);
    }

    public List<HistoricoPreco> listarTodos() {
        return historicoDAO.listarTodos();
    }

    public List<HistoricoPreco> buscarPorData(LocalDate data) {
        return historicoDAO.buscarPorData(data);
    }

    public void atualizar(HistoricoPreco historico) throws Exception {
        historicoDAO.atualizar(historico);
    }

    public void deletar(Integer id) throws Exception {
        historicoDAO.deletar(id);
    }
}
