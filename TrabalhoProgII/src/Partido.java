import java.util.*;

public class Partido {
  private int numero, votosLegenda;
  private String nome, sigla;
  private ArrayList<Candidato> candidatos = new ArrayList<Candidato>();

  public Partido(int numero, int votosLegenda, String nome, String sigla) {
    this.numero = numero;
    this.votosLegenda = votosLegenda;
    this.nome = nome;
    this.sigla = sigla;
  }

  public Candidato getCandidatoMaisVotado() {
    if (candidatos.size() > 0) {
      ArrayList<Candidato> listaCandidatos = this.candidatos;
      Collections.sort(listaCandidatos, new ComparadorCandidatoVotosNominais());

      return listaCandidatos.get(0);
    }
    return null;
  }

  public void adicionaCandidato(Candidato candidato) {
    this.candidatos.add(candidato);
  }

  public void adicionaListaCandidatos(ArrayList<Candidato> lista) {
    this.candidatos = lista;
  }

  public int getTotalVotosValidos() {
    int total = 0;

    for (Candidato c : this.candidatos) {
      if (c.getDestinoVoto().equals("Válido")) {
        total += c.getVotosNominais();
      }
    }

    return total + this.votosLegenda;
  }

  public int getTotalVotosNominais() {
    return this.getTotalVotosValidos() - this.votosLegenda;
  }

  public ArrayList<Candidato> getListaCandidatos() {
    return this.candidatos;
  }

  public int getQuantidadeCandidatosEleitos() {
    int total = 0;

    for (Candidato c : this.candidatos) {
      if (c.getSituacao().equals("Eleito")) {
        total++;
      }
    }

    return total;
  }

  public int getNumero() {
    return this.numero;
  }

  public int getVotosLegenda() {
    return this.votosLegenda;
  }

  public String getNome() {
    return this.nome;
  }

  public String getSigla() {
    return this.sigla;
  }
}
