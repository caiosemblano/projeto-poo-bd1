package br.inatel.dao;

import java.util.List;

public interface Repositorio<T, ID> {
    void inserir(T entidade);
    T buscarPorId(ID id);
    List<T> listarTodos();
    void atualizar(T entidade);
    void deletar(ID id);
}