package br.inatel.service;

import br.inatel.dao.CarteiraDAO;
import br.inatel.model.Carteira;

import java.util.List;

public class CarteiraService {

    private final CarteiraDAO carteiraDAO;

    public CarteiraService(CarteiraDAO carteiraDAO) {
        this.carteiraDAO = carteiraDAO;
    }

    public void inserir(Carteira carteira) {
        carteiraDAO.inserir(carteira);
    }

    public Carteira buscarPorId(Integer id) {
        return carteiraDAO.buscarPorId(id);
    }

    public List<Carteira> listarTodos() {
        return carteiraDAO.listarTodos();
    }

    public void atualizar(Carteira carteira) {
        carteiraDAO.atualizar(carteira);
    }

    public void deletar(Integer id) {
        carteiraDAO.deletar(id);
    }
}
