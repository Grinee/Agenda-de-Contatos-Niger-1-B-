package View;

import Model.Contatos;
import java.util.List;

public class ContatosView {

    public void mostrarContatos(List<Contatos> contatos) {
        System.out.println("\n=== Lista de Contatos ===");
        if (contatos.isEmpty()) {
            System.out.println("Nenhum contato cadastrado.");
        } else {
            for (Contatos c : contatos) {
                System.out.println(c);
            }
        }
        System.out.println("========================\n");
    }
}
