import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Relatorios {

  private static Boolean candidatoEleito(Vereador vereador) {
    return vereador.getSituacao() == "Eleito";
  }

  private static void exibeVereador(
    int n,
    String nome,
    String nomeUrna,
    String partido,
    int votos
  ) {
    System.out.printf(
      "%d - %s / %s (%s, %d votos)",
      n,
      nome.toUpperCase(),
      nomeUrna.toUpperCase(),
      partido.toUpperCase(),
      votos
    );
  }

  public static void Relatorio1(ListaVereadores vereadores) {
    System.out.println("Número de vagas: " + vereadores.getVagas());
  }

  public static void Relatorio2(
    ListaVereadores vereadores,
    ListaPartidos partidos
  ) {
    System.out.println("Vereadores eleitos:");

    int i = 1;
    for (Vereador v : vereadores.getVereadores()) {
      if (candidatoEleito(v)) {
        for (Partido p : partidos.getListaPartidos()) {
          if (p.getNumero() == v.getNumeroPartido()) {
            String nomePartido = p.getNome();
            exibeVereador(
              i,
              v.getNome(),
              v.getNomeUrna(),
              nomePartido,
              v.getVotosNominais()
            );
          }
        }
      }
    }
  }

  public static void Relatorio3(ListaVereadores vereadores) {
    ArrayList<Vereador> vereadoresEmOrdem = Collections.sort(vereadores.getVereadores(), new Comparator<Vereador>() {
        @Override
        public int compare(Vereador v1, Vereador v2) {
            if (v1.getVotosNominais() == v2.getVotosNominais()) {
                return 1;
            } else if (v1.getVotosNominais() < v2.getVotosNominais()) {
                return -1;
            } else {
                return 1;
            }
        }
    }); 
  

    for (int i = 0; i < vereadores.getVagas(); i++) {
      exibeVereador(
        i + 1,
        vereadoresEmOrdem.get(i).getNome(),
        vereadoresEmOrdem.get(i).getNomeUrna(),
        "<Nome do partido aqui>",
        vereadoresEmOrdem.get(i).getVotosNominais()
      );
    }
  }
}
