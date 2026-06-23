package br.inatel.service;

import br.inatel.dao.ObjetivoDAO;
import br.inatel.model.Objetivo;

import java.util.List;

public class ObjetivoService {

    private final ObjetivoDAO objetivoDAO;

    public ObjetivoService(ObjetivoDAO objetivoDAO) {
        this.objetivoDAO = objetivoDAO;
    }

    public void inserir(Objetivo objetivo) throws Exception {
        objetivoDAO.inserir(objetivo);
    }

    public Objetivo buscarPorId(Integer id) {
        return objetivoDAO.buscarPorId(id);
    }

    public List<Objetivo> listarTodos() {
        return objetivoDAO.listarTodos();
    }

    public List<Objetivo> buscarPorStatus(String status) {
        return objetivoDAO.buscarPorStatus(status);
    }

    public void atualizar(Objetivo objetivo) throws Exception {
        objetivoDAO.atualizar(objetivo);
    }

    public void deletar(Integer id) throws Exception {
        objetivoDAO.deletar(id);
    }
}
