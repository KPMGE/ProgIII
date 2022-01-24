public class Partido {
  private int numero, votosLegenda;
  private String nome, sigla;

  public Partido(int numero, int votosLegenda, String nome, String sigla) {
    this.numero = numero;
    this.votosLegenda = votosLegenda;
    this.nome = nome;
    this.sigla = sigla;
  }

  int getNumero() {
    return this.numero;
  }

  int getVotosLegenda() {
    return this.votosLegenda;
  }

  String getNome() {
    return this.nome;
  }

  String getSigla() {
    return this.sigla;
  }
}
