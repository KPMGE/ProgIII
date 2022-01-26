import java.util.Comparator;

public class ComparadorCandidatoVotosNominais implements Comparator<Candidato> {
  public int compare(Candidato c1, Candidato c2) {
    return c2.getVotosNominais() - c1.getVotosNominais();
  }
}
