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

    public void listarTodos() {
        System.out.println("\n--- Lista de Históricos ---");
        List<HistoricoPreco> historicos = historicoDAO.listarTodos();

        if (historicos.isEmpty()) {
            System.out.println("Nenhum histórico cadastrado.");
            return;
        }

        System.out.printf("%-5s | %-8s | %-12s | %-15s | %-15s | %-15s | %-15s | %-15s\n",
                "ID", "ID Ativo", "Data", "Abertura", "Fechamento", "Máximo", "Mínimo", "Volume");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------");
        for (HistoricoPreco h : historicos) {
            System.out.printf("%-5d | %-8d | %-12s | R$ %-12s | R$ %-12s | R$ %-12s | R$ %-12s | R$ %-12s\n",
                    h.getIdHistorico(),
                    h.getAtivo() != null ? h.getAtivo().getIdAtivo() : 0,
                    h.getData(),
                    h.getPrecoAbertura(),
                    h.getPrecoFechamento(),
                    h.getPrecoMaximo(),
                    h.getPrecoMinimo(),
                    h.getVolumeNegociado());
        }
    }

    public void buscarPorData(LocalDate data) {
        System.out.println("\n--- Históricos em " + data + " ---");
        List<HistoricoPreco> historicos = historicoDAO.buscarPorData(data);

        if (historicos.isEmpty()) {
            System.out.println("Nenhum histórico encontrado para esta data.");
            return;
        }

        System.out.printf("%-5s | %-8s | %-12s | %-15s | %-15s | %-15s | %-15s | %-15s\n",
                "ID", "ID Ativo", "Data", "Abertura", "Fechamento", "Máximo", "Mínimo", "Volume");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------");
        for (HistoricoPreco h : historicos) {
            System.out.printf("%-5d | %-8d | %-12s | R$ %-12s | R$ %-12s | R$ %-12s | R$ %-12s | R$ %-12s\n",
                    h.getIdHistorico(),
                    h.getAtivo() != null ? h.getAtivo().getIdAtivo() : 0,
                    h.getData(),
                    h.getPrecoAbertura(),
                    h.getPrecoFechamento(),
                    h.getPrecoMaximo(),
                    h.getPrecoMinimo(),
                    h.getVolumeNegociado());
        }
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
