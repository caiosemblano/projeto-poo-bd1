package br.inatel;

import br.inatel.dao.InvestidorDAO;
import br.inatel.model.Investidor;
import java.time.LocalDate;

public class ValidadorDAO {
    
    public static void main(String[] args) {
        System.out.println("Iniciando testes dos DAOs...");
        testarInvestidorDAO();
        System.out.println("Testes finalizados.");
    }

    private static void testarInvestidorDAO() {
        InvestidorDAO dao = new InvestidorDAO();
        
        System.out.println("--- Testando InvestidorDAO ---");
        
        System.out.println("1. Testando Inserir...");
        Investidor inv = new Investidor();
        inv.setNome("Teste Validador");
        // Using a timestamp to avoid UNIQUE constraint violation on subsequent runs
        inv.setEmail("teste" + System.currentTimeMillis() + "@email.com");
        inv.setTelefone("11900000000");
        inv.setSenha("senha123");
        inv.setDataCadastro(LocalDate.now());
        inv.setStatus("Ativo");
        
        dao.inserir(inv);
        System.out.println("Inserido com ID: " + inv.getIdInvestidor());

        System.out.println("2. Testando Buscar por ID...");
        Investidor buscado = dao.buscarPorId(inv.getIdInvestidor());
        System.out.println("Encontrado: " + (buscado != null ? buscado.getNome() : "Nenhum"));

        System.out.println("3. Testando Atualizar...");
        if (buscado != null) {
            buscado.setNome("Nome Atualizado");
            dao.atualizar(buscado);
            Investidor atualizado = dao.buscarPorId(inv.getIdInvestidor());
            System.out.println("Nome atualizado para: " + atualizado.getNome());
        }

        System.out.println("4. Testando Deletar...");
        if (buscado != null) {
            dao.deletar(inv.getIdInvestidor());
            Investidor deletado = dao.buscarPorId(inv.getIdInvestidor());
            System.out.println("Deletado com sucesso: " + (deletado == null));
        }
    }
}
