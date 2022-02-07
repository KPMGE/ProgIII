import java.util.Calendar;
import java.util.Collections;
import java.util.ArrayList;

public class Relatorios {
  public static void relatorio1(ArrayList<Candidato> listaCandidatos) {
    System.out.println("Número de vagas: " + Utilitarios.calculaNumeroVagas(listaCandidatos));
    System.out.println();
  }

  public static void relatorio2(ArrayList<Candidato> listaCandidatos) {
    System.out.println("Vereadores eleitos:");

    // cria uma cópida da lista de candidatos eleitos e a ordena com base no
    // ComparadorCandidatoVotosNominais
    ArrayList<Candidato> candidatosEleitos = Utilitarios.retornaCandidatosEleitos(listaCandidatos);
    ArrayList<Candidato> eleitos = new ArrayList<Candidato>(candidatosEleitos);
    Collections.sort(eleitos, new ComparadorCandidatoVotosNominais());

    // percorre a lista ordenada exibindo as informações
    for (int i = 0; i < Utilitarios.calculaNumeroVagas(listaCandidatos); i++) {
      Candidato vereador = eleitos.get(i);
      String nome = vereador.getNome();
      String nomeUrna = vereador.getNomeUrna();
      String partidoSigla = vereador.getPartido().getSigla();
      int votosNominais = vereador.getVotosNominais();

      System.out.printf("%d - %s / %s (%s, %d %s)\n", i + 1, nome, nomeUrna, partidoSigla, votosNominais,
          votosNominais > 1 ? "votos" : "voto");
    }

    System.out.println();
  }

  public static void relatorio3(ArrayList<Candidato> listaCandidatos) {
    System.out.println("Candidatos mais votados (em ordem decrescente de votação e respeitando número de vagas):");

    // clonando lista de candidatos e ordenando-a
    ArrayList<Candidato> candidatos = new ArrayList<Candidato>(listaCandidatos);
    Collections.sort(candidatos, new ComparadorCandidatoVotosNominais());

    // percorrendo a lista ordenada e exibindo informações no stdout
    for (int i = 0; i < Utilitarios.calculaNumeroVagas(listaCandidatos); i++) {
      Candidato vereador = candidatos.get(i);

      String nome = vereador.getNome();
      String nomeUrna = vereador.getNomeUrna();
      String partidoSigla = vereador.getPartido().getSigla();
      int votosNominais = vereador.getVotosNominais();

      System.out.printf("%d - %s / %s (%s, %d %s)\n", i + 1, nome, nomeUrna,
          partidoSigla, votosNominais, votosNominais > 1 ? "votos" : "voto");
    }

    System.out.println();
  }

  public static void relatorio4(ArrayList<Candidato> listaCandidatos) {
    System.out.println("Teriam sido eleitos se a votação fosse majoritária, e não foram eleitos:");
    System.out.println("(com sua posição no ranking de mais votados)");

    // clonando lista de candidatos e ordenando-a
    ArrayList<Candidato> candidatosMaisVotados = new ArrayList<Candidato>(listaCandidatos);
    Collections.sort(candidatosMaisVotados, new ComparadorCandidatoVotosNominais());

    // percorrendo a lista ordenada e exibindo informações no stdout
    for (int i = 0; i < Utilitarios.calculaNumeroVagas(listaCandidatos); i++) {
      if (!Utilitarios.candidatoEleito(candidatosMaisVotados.get(i))) {
        String nome = candidatosMaisVotados.get(i).getNome();
        String nomeUrna = candidatosMaisVotados.get(i).getNomeUrna();
        String partidoSigla = candidatosMaisVotados.get(i).getPartido().getSigla();
        int votosNominais = candidatosMaisVotados.get(i).getVotosNominais();

        System.out.printf("%d - %s / %s (%s, %d votos)\n", i + 1, nome,
            nomeUrna, partidoSigla, votosNominais, votosNominais > 1 ? "votos" : "voto");
      }
    }

    System.out.println();
  }

  public static void relatorio5(ArrayList<Candidato> listaCandidatos) {
    System.out.println("Eleitos, que se beneficiaram do sistema proporcional:");
    System.out.println("(com sua posição no ranking de mais votados)");

    // clonando lista de candidatos e ordenando-a
    ArrayList<Candidato> candidatosMaisVotados = new ArrayList<Candidato>(listaCandidatos);
    Collections.sort(candidatosMaisVotados, new ComparadorCandidatoVotosNominais());

    // percorrendo a lista ordenada e exibindo informações no stdout
    for (int i = Utilitarios.calculaNumeroVagas(listaCandidatos); i < candidatosMaisVotados.size() - 1; i++) {
      if (Utilitarios.candidatoEleito(candidatosMaisVotados.get(i))) {

        String nome = candidatosMaisVotados.get(i).getNome();
        String nomeUrna = candidatosMaisVotados.get(i).getNomeUrna();
        String sigla = candidatosMaisVotados.get(i).getPartido().getSigla();
        int votosNominais = candidatosMaisVotados.get(i).getVotosNominais();

        System.out.printf("%d - %s / %s (%s, %d %s)\n", i + 1, nome, nomeUrna, sigla, votosNominais,
            votosNominais > 1 ? "votos" : "voto");
      }
    }

    System.out.println();
  }

  // eleitos/candidatos
  public static void relatorio6(ArrayList<Partido> listaPartidos) {
    System.out.println("Votação dos partidos e número de candidatos eleitos:");

    // clonando a lista de partidos e ordenando-a
    ArrayList<Partido> partidos = new ArrayList<Partido>(listaPartidos);
    Collections.sort(partidos, new ComparadorPartidoTotalVotos());

    // percorrendo a lista ordenada e exibindo informações no stdout
    for (int i = 0; i < partidos.size(); i++) {
      Partido p = partidos.get(i);

      int votosValidos = p.getTotalVotosValidos();
      int votosNominais = p.getTotalVotosNominais();
      int quantidadeEleitos = p.getQuantidadeCandidatosEleitos();

      String mensagemVotosNominais = votosNominais > 1 ? "nominais" : "nominal";
      String mensagemEleitos = quantidadeEleitos <= 1 ? "candidato eleito" : "candidatos eleitos";

      System.out.printf("%d - %s - %d, %d %s", i + 1, p.getSigla(), p.getNumero(), votosValidos,
          votosValidos == 0 ? "voto" : "votos");
      System.out.printf(" (%d %s e %d de legenda), %d %s\n", votosNominais, mensagemVotosNominais, p.getVotosLegenda(),
          quantidadeEleitos, mensagemEleitos);
    }

    System.out.println();
  }

  public static void relatorio7(ArrayList<Partido> listaPartidos) {
    System.out.println("Votação dos partidos (apenas votos de legenda):");

    // clonando a lista e ordenando-a com base no ComparadorPartidoVotosLegenda
    ArrayList<Partido> partidos = new ArrayList<Partido>(listaPartidos);
    Collections.sort(partidos, new ComparadorPartidoVotosLegenda());

    // percorrendo a lista e exibindo as informações relevantes
    for (int i = 0; i < partidos.size(); i++) {
      Partido p = partidos.get(i);

      int votosLegenda = p.getVotosLegenda();
      int totalVotos = p.getTotalVotosValidos();
      float porcentagem = Utilitarios.calculaPorcentagem(totalVotos, votosLegenda);
      String mensagemVotosLegenda = votosLegenda > 1 ? "votos" : "voto";

      System.out.printf("%d - %s - %d, %d %s de legenda", i + 1, p.getSigla(),
          p.getNumero(), votosLegenda, mensagemVotosLegenda);

      if (Double.isNaN(porcentagem)) {
        System.out.println(" (proporção não calculada, 0 voto no partido)");
      } else {
        System.out.printf(" (%.2f%% do total do partido)\n", porcentagem);
      }
    }

    System.out.println();
  }

  public static void relatorio8(ArrayList<Partido> listaPartidos) {
    System.out.println("Primeiro e último colocados de cada partido:");

    // clonando a lsita e ordenando-a com base no ComparadorPartidoTotalVotos
    ArrayList<Partido> partidos = new ArrayList<>(listaPartidos);
    Collections.sort(partidos, new ComparadorPartidoVotosCandidatos());

    // percorrendo a lista e exibindo as informações relevantes
    for (int i = 0; i < partidos.size(); i++) {
      Partido p = partidos.get(i);

      // exibe apenas se o total de votos válidos for maior que 0
      if (p.getTotalVotosValidos() > 0) {
        // clonando a lista de candidatos e ordenando-a com base no
        // ComparadorCandidatoVotosNominais
        ArrayList<Candidato> candidatos = new ArrayList<Candidato>(p.getListaCandidatos());
        Collections.sort(candidatos, new ComparadorCandidatoVotosNominais());

        // extraindo primeiro e ultimo candidatos
        Candidato primeiroCandidato = candidatos.get(0);
        Candidato ultimoCandidato = candidatos.get(candidatos.size() - 1);

        // string para ajustar plural dependendo da quantidade de votos nominais
        String mensagemPrimeiro = primeiroCandidato.getVotosNominais() > 1 ? "votos" : "voto";
        String mensagemUltimo = ultimoCandidato.getVotosNominais() > 1 ? "votos" : "voto";

        System.out.printf("%d - %s - %d, %s (%d, %d %s) / %s (%d, %d %s)\n", i + 1,
            p.getSigla(),
            p.getNumero(),
            primeiroCandidato.getNomeUrna(),
            primeiroCandidato.getNumero(), primeiroCandidato.getVotosNominais(), mensagemPrimeiro,
            ultimoCandidato.getNomeUrna(),
            ultimoCandidato.getNumero(), ultimoCandidato.getVotosNominais(), mensagemUltimo);
      }
    }

    System.out.println();
  }

  public static void relatorio9(ArrayList<Candidato> listaCandidatos, Calendar dataEleicao) {
    System.out.println("Eleitos, por faixa etária (na data da eleição):");

    // quantidade de candidatos em cada faixa etária
    int qtdMenor30 = 0;
    int qtdEntre30e40 = 0;
    int qtdEntre40e50 = 0;
    int qtdEntre50e60 = 0;
    int qtdMaior60 = 0;

    // todos os candidatos eleitos
    ArrayList<Candidato> eleitos = Utilitarios.retornaCandidatosEleitos(listaCandidatos);

    // percorre a lista de candidatos, calculando a idade de cada candidato e então
    // atualizando as variáveis auxiliares.
    for (Candidato c : eleitos) {
      int idade = Utilitarios.calculaIdade(c.getDataNascimento(), dataEleicao);

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
    System.out.printf("      Idade < 30: %d (%.2f%%)\n", qtdMenor30,
        Utilitarios.calculaPorcentagem(totalCandidatos, qtdMenor30));
    System.out.printf("30 <= Idade < 40: %d (%.2f%%)\n", qtdEntre30e40,
        Utilitarios.calculaPorcentagem(totalCandidatos, qtdEntre30e40));
    System.out.printf("40 <= Idade < 50: %d (%.2f%%)\n", qtdEntre40e50,
        Utilitarios.calculaPorcentagem(totalCandidatos, qtdEntre40e50));
    System.out.printf("50 <= Idade < 60: %d (%.2f%%)\n", qtdEntre50e60,
        Utilitarios.calculaPorcentagem(totalCandidatos, qtdEntre50e60));
    System.out.printf("60 <= Idade     : %d (%.2f%%)\n", qtdMaior60,
        Utilitarios.calculaPorcentagem(totalCandidatos, qtdMaior60));

    System.out.println();
  }

  public static void relatorio10(ArrayList<Candidato> listaCandidatos) {
    System.out.println("Eleitos, por sexo:");

    ArrayList<Candidato> eleitos = Utilitarios.retornaCandidatosEleitos(listaCandidatos);

    int homem = 0, mulher = 0;

    // percorrendo a lista de candidatos incrementando a quantidade de homens e
    // mulheres
    for (Candidato c : eleitos) {
      if (c.getSexo().equals("M")) {
        homem++;
      } else {
        mulher++;
      }
    }

    // exibindo as informações, já com as porcentagens
    System.out.printf("Feminino:  %d (%.2f%%)\n", mulher, Utilitarios.calculaPorcentagem(eleitos.size(), mulher));
    System.out.printf("Masculino: %d (%.2f%%)\n", homem, Utilitarios.calculaPorcentagem(eleitos.size(), homem));
    System.out.println();
  }

  public static void relatorio11(ArrayList<Partido> listaPartidos) {
    int totalVotosValidos = Utilitarios.retornaTotalVotosValidos(listaPartidos);
    int totalVotosLegenda = Utilitarios.retornaTotalVotosLegenda(listaPartidos);
    int totalVotosNominais = totalVotosValidos - totalVotosLegenda;

    System.out.println("Total de votos válidos:    " + totalVotosValidos);
    System.out.printf("Total de votos nominais:   %d (%.2f%%)\n", totalVotosNominais,
        Utilitarios.calculaPorcentagem(totalVotosValidos, totalVotosNominais));
    System.out.printf("Total de votos de legenda: %d (%.2f%%)\n\n", totalVotosLegenda,
        Utilitarios.calculaPorcentagem(totalVotosValidos, totalVotosLegenda));
  }
}
