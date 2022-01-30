import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Period;
import java.util.Calendar;
import java.util.Collections;
import java.util.ArrayList;

public class Relatorios {
  private ArrayList<Partido> listaPartidos = new ArrayList<Partido>();
  private ArrayList<Candidato> listaCandidatos = new ArrayList<Candidato>();
  private Calendar dataEleicao;

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

  private static Boolean candidatoEleito(Candidato c) {
    return c.getSituacao().equals("Eleito");
  }

  private int calculaNumeroVagas() {
    int count = 0;
    for (Candidato c : this.listaCandidatos) {
      if (candidatoEleito(c)) {
        count++;
      }
    }

    return count;
  }

  private ArrayList<Candidato> retornaCandidatosEleitos() {
    ArrayList<Candidato> eleitos = new ArrayList<Candidato>();

    for (Candidato c : this.listaCandidatos) {
      if (candidatoEleito(c)) {
        eleitos.add(c);
      }
    }

    return eleitos;
  }

  private int retornaTotalVotosValidos() {
    int total = 0;

    for (Partido p : this.listaPartidos) {
      total += p.getTotalVotosValidos();
    }

    return total;
  }

  private int retornaTotalVotosLegenda() {
    int total = 0;

    for (Partido p : this.listaPartidos) {
      total += p.getVotosLegenda();
    }

    return total;
  }

  private float calculaPorcentagem(float total, float num) {
    return (100 * num) / total;
  }

  public Relatorios(String csvPartidos, String csvCandidatos, Calendar dataEleicao) {
    // lê os partidos e adiciona-os à lista de partidos
    this.lePartidos(csvPartidos);
    // lê os candidatos e adiciona-os à lista de candidatos
    this.leCandidatos(csvCandidatos);
    // cria o relacionamento entre os candidatos e partidos (cada partido tem uma
    // lista de candidatos, e cada candidato tem um partido associado)
    this.relacionaPartidoComCandidatos();
    this.dataEleicao = dataEleicao;
  }

  public void relatorio1() {
    System.out.println("Número de vagas: " + this.calculaNumeroVagas());
    System.out.println();
  }

  public void relatorio2() {
    System.out.println("Vereadores eleitos:");

    ArrayList<Candidato> eleitos = retornaCandidatosEleitos();

    Collections.sort(eleitos, new ComparadorCandidatoVotosNominais());

    for (int i = 0; i < this.calculaNumeroVagas(); i++) {
      Candidato vereador = eleitos.get(i);

      System.out.printf("%d - %s / %s (%s, %d votos)\n", i + 1, vereador.getNome(), vereador.getNomeUrna(),
          vereador.getPartido().getSigla(), vereador.getVotosNominais());
    }

    System.out.println();
  }

  public void relatorio3() {
    System.out.println("Candidatos mais votados (em ordem decrescente de votação e respeitando número de vagas):");
    // clonando lista de candidatos e ordenando-a
    ArrayList<Candidato> candidatos = new ArrayList<Candidato>(this.listaCandidatos);
    Collections.sort(candidatos, new ComparadorCandidatoVotosNominais());

    for (int i = 0; i < this.calculaNumeroVagas(); i++) {
      Candidato vereador = candidatos.get(i);

      System.out.printf("%d - %s / %s (%s, %d votos)\n", i + 1, vereador.getNome(), vereador.getNomeUrna(),
          vereador.getPartido().getSigla(), vereador.getVotosNominais());
    }

    System.out.println();
  }

  public void relatorio4() {
    System.out.println("Teriam sido eleitos se a votação fosse majoritária, e não foram eleitos:");
    ArrayList<Candidato> candidatosMaisVotados = new ArrayList<Candidato>(this.listaCandidatos);
    Collections.sort(candidatosMaisVotados, new ComparadorCandidatoVotosNominais());
    for (int i = 0; i < 15; i++) {
      if (!candidatoEleito(candidatosMaisVotados.get(i))) {
        System.out.printf("%d - %s / %s (%s, %d votos)\n", i + 1, candidatosMaisVotados.get(i).getNome(),
            candidatosMaisVotados.get(i).getNomeUrna(),
            candidatosMaisVotados.get(i).getPartido().getSigla(), candidatosMaisVotados.get(i).getVotosNominais());
      }
    }

    System.out.println();
  }

  public void relatorio5() {
    System.out.println("Eleitos, que se beneficiaram do sistema proporcional:");
    ArrayList<Candidato> candidatosMaisVotados = new ArrayList<Candidato>(this.listaCandidatos);
    Collections.sort(candidatosMaisVotados, new ComparadorCandidatoVotosNominais());
    for (int i = 14; i < candidatosMaisVotados.size() - 1; i++) {
      if (candidatoEleito(candidatosMaisVotados.get(i))) {
        System.out.printf("%d - %s / %s (%s, %d votos)\n", i + 1, candidatosMaisVotados.get(i).getNome(),
            candidatosMaisVotados.get(i).getNomeUrna(),
            candidatosMaisVotados.get(i).getPartido().getSigla(), candidatosMaisVotados.get(i).getVotosNominais());
      }
    }
    System.out.println();
  }

  // TODO: colocar 'eleito' e 'candidato' quando houverem apenas 0 ou 1
  // eleitos/candidatos
  public void relatorio6() {
    System.out.println("Votação dos partidos e número de candidatos eleitos:");

    // clonando a lista de partidos e ordenando-a
    ArrayList<Partido> partidos = new ArrayList<Partido>(this.listaPartidos);
    Collections.sort(partidos, new ComparadorPartidoTotalVotos());

    for (int i = 0; i < partidos.size(); i++) {
      Partido p = partidos.get(i);
      System.out.printf("%d - %s - %d, %d votos (%d nominais e %d de legenda), %d candidatos eleitos\n", i + 1,
          p.getSigla(), p.getNumero(), p.getTotalVotosValidos(), p.getTotalVotosNominais(), p.getVotosLegenda(),
          p.getQuantidadeCandidatosEleitos());
    }

    System.out.println();
  }

  public void relatorio7() {
    System.out.println("Votação dos partidos (apenas votos de legenda):");

    ArrayList<Partido> partidos = new ArrayList<Partido>(this.listaPartidos);
    Collections.sort(partidos, new ComparadorPartidoVotosLegenda());

    for (int i = 0; i < partidos.size(); i++) {
      Partido p = partidos.get(i);
      int votosLegenda = p.getVotosLegenda();
      int totalVotos = p.getTotalVotosValidos();
      float porcentagem = calculaPorcentagem(totalVotos, votosLegenda);

      System.out.printf("%d - %s - %d, %d votos de legenda", i + 1, p.getSigla(),
          p.getNumero(), votosLegenda);

      if (Double.isNaN(porcentagem)) {
        System.out.println("(proporção não calculada, 0 voto no partido)");
      } else {
        System.out.printf("(%.2f%% do total do partido)\n", porcentagem);
      }
    }

    System.out.println();
  }

  // TODO: Corrigir
  // public void relatorio8() {
  // System.out.println("Primeiro e último colocados de cada partido:");

  // for (int i = 0; i < listaPartidos.size(); i++) {
  // Partido p = listaPartidos.get(i);
  // Candidato primeiroCandidato = p.getListaCandidatos().get(0);
  // Candidato ultimoCandidato =
  // p.getListaCandidatos().get(p.getListaCandidatos().size() - 1);

  // System.out.printf("%d - %s - %d, %s (%d, %d votos) / %s (%d, %d votos)\n", i,
  // p.getNome().toUpperCase(),
  // p.getNumero(),
  // primeiroCandidato.getNome(),
  // primeiroCandidato.getNumero(), primeiroCandidato.getVotosNominais(),
  // ultimoCandidato.getNomeUrna(),
  // ultimoCandidato.getNumero(), ultimoCandidato.getVotosNominais());
  // }

  // System.out.println();
  // }

  private static int calculaIdade(Calendar dataNascimento, Calendar dataAtual) {
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

  public void relatorio9() {
    // quantidade de candidatos em cada faixa etária
    int qtdMenor30 = 0;
    int qtdEntre30e40 = 0;
    int qtdEntre40e50 = 0;
    int qtdEntre50e60 = 0;
    int qtdMaior60 = 0;

    // todos os candidatos eleitos
    ArrayList<Candidato> eleitos = retornaCandidatosEleitos();

    for (Candidato c : eleitos) {
      int idade = calculaIdade(c.getDataNascimento(), this.dataEleicao);

      if (idade < 30) {
        qtdMenor30++;
        continue;
      }

      if (idade < 40) {
        qtdEntre30e40++;
        continue;
      }

      if (idade < 50) {
        qtdEntre40e50++;
        continue;
      }

      if (idade < 60) {
        qtdEntre50e60++;
        continue;
      }

      qtdMaior60++;
    }

    int totalCandidatos = eleitos.size();

    // exibe a distribuição etária, formatada
    System.out.println("eleitos: " + totalCandidatos);
    System.out.printf("      Idade < 30: %d (%.2f%%)\n", qtdMenor30,
        calculaPorcentagem(totalCandidatos, qtdMenor30));
    System.out.printf("30 <= Idade < 40: %d (%.2f%%)\n", qtdEntre30e40,
        calculaPorcentagem(totalCandidatos, qtdEntre30e40));
    System.out.printf("40 <= Idade < 50: %d (%.2f%%)\n", qtdEntre40e50,
        calculaPorcentagem(totalCandidatos, qtdEntre40e50));
    System.out.printf("50 <= Idade < 60: %d (%.2f%%)\n", qtdEntre50e60,
        calculaPorcentagem(totalCandidatos, qtdEntre50e60));
    System.out.printf("60 <= Idade     : %d (%.2f%%)\n", qtdMaior60,
        calculaPorcentagem(totalCandidatos, qtdMaior60));
  }

  public void relatorio10() {
    System.out.println("Eleitos, por sexo:");
    ArrayList<Candidato> eleitos = this.retornaCandidatosEleitos();

    int homem = 0, mulher = 0;
    for (Candidato c : eleitos) {
      if (c.getSexo().equals("M")) {
        homem++;
      } else {
        mulher++;
      }
    }

    System.out.printf("Feminino: %d (%.2f%%)\n", mulher, calculaPorcentagem(eleitos.size(), mulher));
    System.out.printf("Masculino: %d (%.2f%%)\n", homem, calculaPorcentagem(eleitos.size(), homem));
  }

  public void relatorio11() {
    int totalVotosValidos = retornaTotalVotosValidos();
    int totalVotosLegenda = retornaTotalVotosLegenda();
    int totalVotosNominais = totalVotosValidos - totalVotosLegenda;

    System.out.println("Total de votos válidos: " + totalVotosValidos);
    System.out.printf("Total de votos nominais: %d (%.2f%%)\n", totalVotosNominais,
        calculaPorcentagem(totalVotosValidos, totalVotosNominais));
    System.out.printf("Total de votos de legenda: %d (%.2f%%)\n", totalVotosLegenda,
        calculaPorcentagem(totalVotosValidos, totalVotosLegenda));
  }
}
