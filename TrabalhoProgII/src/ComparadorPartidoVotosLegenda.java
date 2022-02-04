import java.util.Comparator;

public class ComparadorPartidoVotosLegenda implements Comparator<Partido> {
  public int compare(Partido p1, Partido p2) {
    return p2.getVotosLegenda() - p1.getVotosLegenda();
  }
}
