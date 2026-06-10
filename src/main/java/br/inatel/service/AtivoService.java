package br.inatel.service;

import br.inatel.dao.AtivoDAO;
import br.inatel.model.Ativo;
import java.util.List;
import java.util.stream.Collectors;

public class AtivoService {

    private final AtivoDAO ativoDAO;

    public AtivoService(AtivoDAO ativoDAO) {
        this.ativoDAO = ativoDAO;
    }

    public void inserir(Ativo ativo) {
        ativoDAO.inserir(ativo);
    }

    public Ativo buscarPorId(Integer id) {
        return ativoDAO.buscarPorId(id);
    }

    public Ativo buscarPorSimbolo(String simbolo) {
        return ativoDAO.buscarPorSimbolo(simbolo);
    }

    public List<Ativo> buscarPorTipo(String tipo) {
        return ativoDAO.listarTodos().stream()
                .filter(ativo -> ativo.getTipoAtivo().equalsIgnoreCase(tipo))
                .collect(Collectors.toList());
    }

    public List<Ativo> listarTodos() {
        return ativoDAO.listarTodos();
    }

    public void atualizar(Ativo ativo) {
        ativoDAO.atualizar(ativo);
    }

    public void deletar(Integer id) {
        ativoDAO.deletar(id);
    }
}
