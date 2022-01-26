import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.ArrayList;

public class Relatorios {
  private ArrayList<Partido> listaPartidos = new ArrayList<Partido>();
  private ArrayList<Candidato> listaCandidatos = new ArrayList<Candidato>();

  private void lePartidos(String csvPartidos) {
    try {
      File csv = new File(csvPartidos);
      FileReader fileReader = new FileReader(csv);
      BufferedReader reader = new BufferedReader(fileReader);

      // lê a primeira linha, para ignorá-la
      String linha = reader.readLine();

      while ((linha = reader.readLine()) != null) {
        // separando elementos do csv
        String[] dadosLinha = linha.split(",");

        int numero = Integer.parseInt(dadosLinha[0]);
        int votosLegenda = Integer.parseInt(dadosLinha[1]);
        String nome = dadosLinha[2];
        String sigla = dadosLinha[3];

        // criando novo partido
        Partido novoPartido = new Partido(numero, votosLegenda, nome, sigla);

        // salvando-o na lista
        this.listaPartidos.add(novoPartido);
      }

      reader.close();
    } catch (FileNotFoundException ex) {
      System.out.println("Arquivo não encontrado!");
      ex.printStackTrace();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public void leCandidatos(String csvCandidatos) {
    try {
      File csv = new File(csvCandidatos);
      FileReader fileReader = new FileReader(csv);
      BufferedReader reader = new BufferedReader(fileReader);

      // lê a primeira linha, ignornado-a
      String linha = reader.readLine();

      while ((linha = reader.readLine()) != null) {
        // dividindo linha
        String[] dadosLinha = linha.split(",");

        // extraindo campos do csv e convertendo para os tipos adequados
        int numero = Integer.parseInt(dadosLinha[0]);
        int votosNominais = Integer.parseInt(dadosLinha[1]);
        String situacao = dadosLinha[2];
        String nome = dadosLinha[3];
        String nomeUrna = dadosLinha[4];
        String sexo = dadosLinha[5];
        String dataNascimentoString = dadosLinha[6];
        String destinoVoto = dadosLinha[7];
        int numeroPartido = Integer.parseInt(dadosLinha[8]);

        // criando data
        Calendar dataNascimento = Calendar.getInstance();
        String[] dadosDataNascimento = dataNascimentoString.split("/");
        int dia = Integer.parseInt(dadosDataNascimento[0]);
        int mes = Integer.parseInt(dadosDataNascimento[1]);
        int ano = Integer.parseInt(dadosDataNascimento[2]);
        dataNascimento.set(ano, mes, dia);

        // criando novo candidato e salvando-o na lista
        Candidato novoCandidato = new Candidato(numero, votosNominais, numeroPartido, situacao, nome, nomeUrna,
            destinoVoto, sexo, dataNascimento);

        this.listaCandidatos.add(novoCandidato);
      }

      reader.close();
    } catch (FileNotFoundException ex) {
      System.out.println("Arquivo não encontrado!");
      ex.printStackTrace();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  private ArrayList<Candidato> retornaCandidatosPorNumeroPartido(int numero) {
    // cria uma nova lista para armazenar os candidatos com o número igual ao
    // parametro <numero>
    ArrayList<Candidato> candidatosComNumeroDoPartido = new ArrayList<Candidato>();

    // percorre a lista de candidatos e, se o número do partido do candidato for
    // igual ao valor do parametro, adicione-o na lista
    for (Candidato c : this.listaCandidatos) {
      if (c.getNumeroPartido() == numero) {
        candidatosComNumeroDoPartido.add(c);
      }
    }

    return candidatosComNumeroDoPartido;
  }

  private void relacionaPartidoComCandidatos() {
    for (Partido p : this.listaPartidos) {
      // obtendo a lista com todos os candidatos cujo número do partido é igual ao
      // partido da iteração atual
      ArrayList<Candidato> candidatosComNumeroDoPartido = retornaCandidatosPorNumeroPartido(p.getNumero());
      // adicionando lista de candidatos ao partido
      p.adicionaListaCandidatos(candidatosComNumeroDoPartido);

      // para cada candidato da lista acima, defina o partido como o partido da
      // iteração atual
      for (Candidato c : candidatosComNumeroDoPartido) {
        c.setPartido(p);
      }
    }
  }

  public Relatorios(String csvPartidos, String csvCandidatos) {
    // lê os partidos e adiciona-os à lista de partidos
    this.lePartidos(csvPartidos);
    // lê os candidatos e adiciona-os à lista de candidatos
    this.leCandidatos(csvCandidatos);
    // cria o relacionamento entre os candidatos e partidos (cada partido tem uma
    // lista de candidatos, e cada candidato tem um partido associado)
    this.relacionaPartidoComCandidatos();
  }

  public void relatorio1() {
    // exibe partidos e candidatos associados (isto é apenas um teste e deve, pois,
    // ser removido)
    for (Partido p : this.listaPartidos) {
      System.out.println("\n----------------------------------\n");
      System.out.println("Partido: " + p.getNome());
      System.out.println("Candidatos:");
      for (Candidato c : p.getListaCandidatos()) {
        System.out.println(c.getNome());
      }
    }
  }
}
