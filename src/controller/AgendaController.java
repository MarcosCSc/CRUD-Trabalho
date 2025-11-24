package controller;

import model.Contato;
import java.util.ArrayList;
import java.util.List;

public class AgendaController {
    private List<Contato> contatos;
    private int proximoId = 1;

    public AgendaController() {
        this.contatos = new ArrayList<>();
    }

    public void adicionar(Contato c) {
        c.setId(proximoId++);
        contatos.add(c);
    }

    public List<Contato> listar() {
        return contatos;
    }

    public boolean atualizar(int id, Contato contatoAtualizado) {
        for (int i = 0; i < contatos.size(); i++) {
            if (contatos.get(i).getId() == id) {
                contatoAtualizado.setId(id);
                contatos.set(i, contatoAtualizado);
                return true;
            }
        }
        return false;
    }

    public boolean remover(int id) {
        return contatos.removeIf(c -> c.getId() == id);
    }
}