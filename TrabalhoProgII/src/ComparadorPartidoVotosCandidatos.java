import java.util.Comparator;

public class ComparadorPartidoVotosCandidatos implements Comparator<Partido> {
  public int compare(Partido p1, Partido p2) {
    if(p1.getCandidatoMaisVotado() == null) return 1; 
    if(p2.getCandidatoMaisVotado() == null) return -1; 

    int votosP1 = p1.getCandidatoMaisVotado().getVotosNominais();
    int votosP2 = p2.getCandidatoMaisVotado().getVotosNominais();

    if(votosP1 < votosP2) return 1;
    if(votosP1 > votosP2) return -1;
    return p1.getNumero() - p2.getNumero();

  }
}
