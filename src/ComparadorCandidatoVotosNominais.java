import java.util.Comparator;
import java.util.Calendar;

public class ComparadorCandidatoVotosNominais implements Comparator<Candidato> {

  public int compare(Candidato c1, Candidato c2) {
    //return c2.getVotosNominais() - c1.getVotosNominais();


    Calendar data = Calendar.getInstance();
    int idadeC1 = Utilitarios.calculaIdade(c1.getDataNascimento(), data);
    int idadeC2 = Utilitarios.calculaIdade(c2.getDataNascimento(), data);

    if(c1.getVotosNominais() > c2.getVotosNominais()) return -1;
    if(c1.getVotosNominais() < c2.getVotosNominais()) return 1;

    return idadeC2 - idadeC1;
  }
}