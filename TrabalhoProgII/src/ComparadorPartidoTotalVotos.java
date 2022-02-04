import java.util.Comparator;

public class ComparadorPartidoTotalVotos implements Comparator<Partido> {
  public int compare(Partido p1, Partido p2) {
    return p2.getTotalVotosValidos() - p1.getTotalVotosValidos();
  }
}
