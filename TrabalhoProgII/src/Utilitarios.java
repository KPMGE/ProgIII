import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.ArrayList;

public class Utilitarios {
  public static ArrayList<Partido> lePartidos(String csvPartidos) {
    ArrayList<Partido> listaPartidos = new ArrayList<Partido>();

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
        listaPartidos.add(novoPartido);
      }

      reader.close();
    } catch (FileNotFoundException ex) {
      System.out.println("Arquivo não encontrado!");
      ex.printStackTrace();
    } catch (IOException ex) {
      ex.printStackTrace();
    }

    return listaPartidos;
  }

  public static ArrayList<Candidato> leCandidatos(String csvCandidatos) {
    ArrayList<Candidato> listaCandidatos = new ArrayList<Candidato>();

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
        String nomeUrna = dadosLinha[4].trim();
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
        if (destinoVoto.equals("Válido")) {
          Candidato novoCandidato = new Candidato(numero, votosNominais, numeroPartido, situacao, nome, nomeUrna,
              destinoVoto, sexo, dataNascimento);

          listaCandidatos.add(novoCandidato);
        }
      }

      reader.close();
    } catch (FileNotFoundException ex) {
      System.out.println("Arquivo não encontrado!");
      ex.printStackTrace();
    } catch (IOException ex) {
      ex.printStackTrace();
    }

    return listaCandidatos;

  }

  private static ArrayList<Candidato> retornaCandidatosPorNumeroPartido(ArrayList<Candidato> listaCandidatos,
      int numero) {
    // cria uma nova lista para armazenar os candidatos com o número igual ao
    // parametro <numero>
    ArrayList<Candidato> candidatosComNumeroDoPartido = new ArrayList<Candidato>();

    // percorre a lista de candidatos e, se o número do partido do candidato for
    // igual ao valor do parametro, adicione-o na lista
    for (Candidato c : listaCandidatos) {
      if (c.getNumeroPartido() == numero) {
        candidatosComNumeroDoPartido.add(c);
      }
    }

    return candidatosComNumeroDoPartido;
  }

  public static void relacionaPartidoComCandidatos(ArrayList<Partido> listaPartidos,
      ArrayList<Candidato> listaCandidatos) {
    for (Partido p : listaPartidos) {
      // obtendo a lista com todos os candidatos cujo número do partido é igual ao
      // partido da iteração atual
      ArrayList<Candidato> candidatosComNumeroDoPartido = retornaCandidatosPorNumeroPartido(listaCandidatos,
          p.getNumero());
      // adicionando lista de candidatos ao partido
      p.adicionaListaCandidatos(candidatosComNumeroDoPartido);

      // para cada candidato da lista acima, defina o partido como o partido da
      // iteração atual
      for (Candidato c : candidatosComNumeroDoPartido) {
        c.setPartido(p);
      }
    }
  }

  public static Boolean candidatoEleito(Candidato c) {
    return c.getSituacao().equals("Eleito");
  }

  public static int calculaNumeroVagas(ArrayList<Candidato> listaCandidatos) {
    int count = 0;
    for (Candidato c : listaCandidatos) {
      if (candidatoEleito(c)) {
        count++;
      }
    }

    return count;
  }

  public static ArrayList<Candidato> retornaCandidatosEleitos(ArrayList<Candidato> listaCandidatos) {
    ArrayList<Candidato> eleitos = new ArrayList<Candidato>();

    for (Candidato c : listaCandidatos) {
      if (candidatoEleito(c)) {
        eleitos.add(c);
      }
    }

    return eleitos;
  }

  public static int retornaTotalVotosValidos(ArrayList<Partido> listaPartidos) {
    int total = 0;

    for (Partido p : listaPartidos) {
      total += p.getTotalVotosValidos();
    }

    return total;
  }

  public static int retornaTotalVotosLegenda(ArrayList<Partido> listaPartidos) {
    int total = 0;

    for (Partido p : listaPartidos) {
      total += p.getVotosLegenda();
    }

    return total;
  }

  public static float calculaPorcentagem(float total, float num) {
    return (100 * num) / total;
  }

  public static int calculaIdade(Calendar dataNascimento, Calendar dataAtual) {
    int anoNascimento = dataNascimento.get(Calendar.YEAR);
    int mesNascimento = dataNascimento.get(Calendar.MONTH);
    int diaNascimento = dataNascimento.get(Calendar.DAY_OF_MONTH);

    int anoAtual = dataAtual.get(Calendar.YEAR);
    int mesAtual = dataAtual.get(Calendar.MONTH);
    int diaAtual = dataAtual.get(Calendar.DAY_OF_MONTH);

    int idade = anoAtual - anoNascimento;

    // se o mês de anoNascimento for maior que o mês atual, ou então se, sendo o
    // mesmo mês, mas o dia de nascimento
    // é maior que o dia atual, então sabemos que a pessoa não fez aniversário
    // ainda, logo, retornamos --idade.
    if (mesNascimento > mesAtual || ((mesAtual == mesNascimento) && (diaNascimento > diaAtual))) {
      return --idade;
    }

    return idade;
  }
}
