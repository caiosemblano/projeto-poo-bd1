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

    public void listarTodos() {
        System.out.println("\n--- Lista de Objetivos ---");
        List<Objetivo> objetivos = objetivoDAO.listarTodos();

        if (objetivos.isEmpty()) {
            System.out.println("Nenhum objetivo cadastrado.");
            return;
        }

        System.out.printf("%-5s | %-12s | %-15s | %-10s | %-15s | %-15s | %-25s\n",
                "ID", "ID Carteira", "Meta Rent.", "Prazo", "Data Criação", "Status", "Descrição");
        System.out.println("-----------------------------------------------------------------------------------------------------------------");
        for (Objetivo o : objetivos) {
            System.out.printf("%-5d | %-12d | %-15s | %-10d | %-15s | %-15s | %-25s\n",
                    o.getIdObjetivo(),
                    o.getCarteira() != null ? o.getCarteira().getIdCarteira() : 0,
                    o.getMetaRentabilidade() != null ? "R$ " + o.getMetaRentabilidade() : "N/A",
                    o.getPrazoMeses(),
                    o.getDataCriacao(),
                    o.getStatus(),
                    o.getDescricao() != null ? o.getDescricao() : "");
        }
    }

    public void buscarPorStatus(String status) {
        System.out.println("\n--- Objetivos com status: " + status + " ---");
        List<Objetivo> objetivos = objetivoDAO.buscarPorStatus(status);

        if (objetivos.isEmpty()) {
            System.out.println("Nenhum objetivo encontrado com esse status.");
            return;
        }

        System.out.printf("%-5s | %-12s | %-15s | %-10s | %-15s | %-15s | %-25s\n",
                "ID", "ID Carteira", "Meta Rent.", "Prazo", "Data Criação", "Status", "Descrição");
        System.out.println("-----------------------------------------------------------------------------------------------------------------");
        for (Objetivo o : objetivos) {
            System.out.printf("%-5d | %-12d | %-15s | %-10d | %-15s | %-15s | %-25s\n",
                    o.getIdObjetivo(),
                    o.getCarteira() != null ? o.getCarteira().getIdCarteira() : 0,
                    o.getMetaRentabilidade() != null ? "R$ " + o.getMetaRentabilidade() : "N/A",
                    o.getPrazoMeses(),
                    o.getDataCriacao(),
                    o.getStatus(),
                    o.getDescricao() != null ? o.getDescricao() : "");
        }
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
