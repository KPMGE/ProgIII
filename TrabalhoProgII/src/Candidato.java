import java.util.Calendar;

public class Candidato {
  private int numero, votosNominais, numeroPartido;
  private String situacao, nome, nomeUrna, destinoVoto, sexo;
  private Calendar dataNascimento;
  private Partido partido;

  public Candidato(int numero, int votosNominais, int numeroPartido, String situacao, String nome, String nomeUrna,
      String destinoVoto, String sexo, Calendar dataNascimento) {
    this.numero = numero;
    this.votosNominais = votosNominais;
    this.numeroPartido = numeroPartido;
    this.situacao = situacao;
    this.nome = nome;
    this.nomeUrna = nomeUrna;
    this.destinoVoto = destinoVoto;
    this.sexo = sexo;
    this.dataNascimento = dataNascimento;
  }

  
  public void setPartido(Partido partido) {
    this.partido = partido;
  }

  public Partido getPartido() {
    return this.partido;
  }

  public int getNumero() {
    return numero;
  }

  public int getVotosNominais() {
    return votosNominais;
  }

  public int getNumeroPartido() {
    return numeroPartido;
  }

  public String getSituacao() {
    return situacao;
  }

  public String getNome() {
    return nome;
  }

  public String getNomeUrna() {
    return nomeUrna;
  }

  public String getDestinoVoto() {
    return destinoVoto;
  }

  public String getSexo() {
    return sexo;
  }

  public Calendar getDataNascimento() {
    return dataNascimento;
  }
}
