import java.util.Calendar;

public class Vereador extends Candidato  {
  public Vereador(int numero, int votosNominais, int numeroPartido, String situacao, String nome, String nomeUrna,
      String destinoVoto, String sexo, Calendar dataNascimento) {
    super(numero, votosNominais, numeroPartido, situacao, nome, nomeUrna, destinoVoto, sexo, dataNascimento);
  }
}
