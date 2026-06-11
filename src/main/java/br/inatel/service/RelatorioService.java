package br.inatel.service;

import br.inatel.dao.RelatorioDAO;
import java.util.List;

public class RelatorioService {

    private final RelatorioDAO relatorioDAO;

    public RelatorioService(RelatorioDAO relatorioDAO) { this.relatorioDAO = relatorioDAO; }

    public List<String[]> relatorioResumoPortfolio() { return relatorioDAO.relatorioResumoPortfolio(); }

    public List<String[]> relatorioHistoricoTransacoes(int idInvestidor) { return relatorioDAO.relatorioHistoricoTransacoes(idInvestidor); }

    public List<String[]> relatorioObjetivosCarteira() { return relatorioDAO.relatorioObjetivosCarteira(); }

    public List<String[]> relatorioComparativoPreco(int idCarteira) { return relatorioDAO.relatorioComparativoPreco(idCarteira); }

    public List<String[]> relatorioComprasEVendas() { return relatorioDAO.relatorioComprasEVendas(); }
}
