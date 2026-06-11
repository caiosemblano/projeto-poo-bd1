package br.inatel.service;

import br.inatel.dao.TransacaoDAO;
import br.inatel.model.Transacao;

import java.util.List;

public class TransacaoService {

    private final TransacaoDAO transacaoDAO;

    public TransacaoService(TransacaoDAO transacaoDAO) {
        this.transacaoDAO = transacaoDAO;
    }

    public void inserir(Transacao transacao) {
        transacaoDAO.inserir(transacao);
    }

    public Transacao buscarPorId(Integer id) {
        return transacaoDAO.buscarPorId(id);
    }

    public List<Transacao> listarTransacoesPorCarteira(Integer carteiraId) {
        return transacaoDAO.listarTransacoesPorCarteira(carteiraId);
    }

    public void deletar(Integer id) {
        transacaoDAO.deletar(id);
    }
}
