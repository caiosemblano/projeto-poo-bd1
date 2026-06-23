package br.inatel.service;

import br.inatel.dao.ObjetivoDAO;
import br.inatel.model.Objetivo;

import java.util.List;

public class ObjetivoService {

    private final ObjetivoDAO objetivoDAO;

    public ObjetivoService(ObjetivoDAO objetivoDAO) {
        this.objetivoDAO = objetivoDAO;
    }

    public void inserir(Objetivo objetivo) {
        try {
            objetivoDAO.inserir(objetivo);
            System.out.println("Objetivo cadastrado com sucesso! ID gerado: " + objetivo.getIdObjetivo());
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar objetivo. Verifique se o ID da Carteira existe.");
        }
    }

    public Objetivo buscarPorId(Integer id) {
        Objetivo objetivo = objetivoDAO.buscarPorId(id);
        if (objetivo == null) {
            System.out.println("Objetivo não encontrado!");
        }
        return objetivo;
    }

    public List<Objetivo> listarTodos() {
        return objetivoDAO.listarTodos();
    }

    public List<Objetivo> buscarPorStatus(String status) {
        return objetivoDAO.buscarPorStatus(status);
    }

    public void atualizar(Objetivo objetivo) {
        try {
            objetivoDAO.atualizar(objetivo);
            System.out.println("Objetivo atualizado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar objetivo: " + e.getMessage());
        }
    }

    public void deletar(Integer id) {
        try {
            objetivoDAO.deletar(id);
            System.out.println("Objetivo deletado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao deletar objetivo: " + e.getMessage());
        }
    }
}
