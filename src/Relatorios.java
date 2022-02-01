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

    ArrayList<Candidato> eleitos = Utilitarios.retornaCandidatosEleitos(listaCandidatos);

    Collections.sort(eleitos, new ComparadorCandidatoVotosNominais());

    for (int i = 0; i < Utilitarios.calculaNumeroVagas(listaCandidatos); i++) {
      Candidato vereador = eleitos.get(i);

      System.out.printf("%d - %s / %s (%s, %d votos)\n", i + 1, vereador.getNome(), vereador.getNomeUrna(),
          vereador.getPartido().getSigla(), vereador.getVotosNominais());
    }

    System.out.println();
  }

  public static void relatorio3(ArrayList<Candidato> listaCandidatos) {
    System.out.println("Candidatos mais votados (em ordem decrescente de votação e respeitando número de vagas):");
    // clonando lista de candidatos e ordenando-a
    ArrayList<Candidato> candidatos = new ArrayList<Candidato>(listaCandidatos);
    Collections.sort(candidatos, new ComparadorCandidatoVotosNominais());

    for (int i = 0; i < Utilitarios.calculaNumeroVagas(listaCandidatos); i++) {
      Candidato vereador = candidatos.get(i);

      System.out.printf("%d - %s / %s (%s, %d votos)\n", i + 1, vereador.getNome(), vereador.getNomeUrna(),
          vereador.getPartido().getSigla(), vereador.getVotosNominais());
    }

    System.out.println();
  }

  public static void relatorio4(ArrayList<Candidato> listaCandidatos) {
    System.out.println("Teriam sido eleitos se a votação fosse majoritária, e não foram eleitos:");

    ArrayList<Candidato> candidatosMaisVotados = new ArrayList<Candidato>(listaCandidatos);
    Collections.sort(candidatosMaisVotados, new ComparadorCandidatoVotosNominais());

    for (int i = 0; i < 15; i++) {
      if (!Utilitarios.candidatoEleito(candidatosMaisVotados.get(i))) {

        System.out.printf("%d - %s / %s (%s, %d votos)\n", i + 1, candidatosMaisVotados.get(i).getNome(),
            candidatosMaisVotados.get(i).getNomeUrna(),
            candidatosMaisVotados.get(i).getPartido().getSigla(), candidatosMaisVotados.get(i).getVotosNominais());
      }
    }

    System.out.println();
  }

  public static void relatorio5(ArrayList<Candidato> listaCandidatos) {
    System.out.println("Eleitos, que se beneficiaram do sistema proporcional:");

    ArrayList<Candidato> candidatosMaisVotados = new ArrayList<Candidato>(listaCandidatos);
    Collections.sort(candidatosMaisVotados, new ComparadorCandidatoVotosNominais());

    for (int i = 14; i < candidatosMaisVotados.size() - 1; i++) {
      if (Utilitarios.candidatoEleito(candidatosMaisVotados.get(i))) {
        System.out.printf("%d - %s / %s (%s, %d votos)\n", i + 1, candidatosMaisVotados.get(i).getNome(),
            candidatosMaisVotados.get(i).getNomeUrna(),
            candidatosMaisVotados.get(i).getPartido().getSigla(), candidatosMaisVotados.get(i).getVotosNominais());
      }
    }
    System.out.println();
  }

  // TODO: colocar 'eleito' e 'candidato' quando houverem apenas 0 ou 1
  // eleitos/candidatos
  public static void relatorio6(ArrayList<Partido> listaPartidos) {
    System.out.println("Votação dos partidos e número de candidatos eleitos:");

    // clonando a lista de partidos e ordenando-a
    ArrayList<Partido> partidos = new ArrayList<Partido>(listaPartidos);
    Collections.sort(partidos, new ComparadorPartidoTotalVotos());

    for (int i = 0; i < partidos.size(); i++) {
      Partido p = partidos.get(i);
      System.out.printf("%d - %s - %d, %d votos (%d nominais e %d de legenda), %d candidatos eleitos\n", i + 1,
          p.getSigla(), p.getNumero(), p.getTotalVotosValidos(), p.getTotalVotosNominais(), p.getVotosLegenda(),
          p.getQuantidadeCandidatosEleitos());
    }

    System.out.println();
  }

  public static void relatorio7(ArrayList<Partido> listaPartidos) {
    System.out.println("Votação dos partidos (apenas votos de legenda):");

    ArrayList<Partido> partidos = new ArrayList<Partido>(listaPartidos);
    Collections.sort(partidos, new ComparadorPartidoVotosLegenda());

    for (int i = 0; i < partidos.size(); i++) {
      Partido p = partidos.get(i);
      int votosLegenda = p.getVotosLegenda();
      int totalVotos = p.getTotalVotosValidos();
      float porcentagem = Utilitarios.calculaPorcentagem(totalVotos, votosLegenda);

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
  // public static void relatorio8() {
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
    for (Candidato c : eleitos) {
      if (c.getSexo().equals("M")) {
        homem++;
      } else {
        mulher++;
      }
    }

    System.out.printf("Feminino: %d (%.2f%%)\n", mulher, Utilitarios.calculaPorcentagem(eleitos.size(), mulher));
    System.out.printf("Masculino: %d (%.2f%%)\n", homem, Utilitarios.calculaPorcentagem(eleitos.size(), homem));
    System.out.println();
  }

  public static void relatorio11(ArrayList<Partido> listaPartidos) {
    int totalVotosValidos = Utilitarios.retornaTotalVotosValidos(listaPartidos);
    int totalVotosLegenda = Utilitarios.retornaTotalVotosLegenda(listaPartidos);
    int totalVotosNominais = totalVotosValidos - totalVotosLegenda;

    System.out.println("Total de votos válidos: " + totalVotosValidos);
    System.out.printf("Total de votos nominais: %d (%.2f%%)\n", totalVotosNominais,
        Utilitarios.calculaPorcentagem(totalVotosValidos, totalVotosNominais));
    System.out.printf("Total de votos de legenda: %d (%.2f%%)\n", totalVotosLegenda,
        Utilitarios.calculaPorcentagem(totalVotosValidos, totalVotosLegenda));
  }
}
