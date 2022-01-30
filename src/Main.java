import java.util.Calendar;

public class Main {
  public static void main(String[] args) {
    if (args.length < 3) {
      System.out.println("Erro! Passe todos os parâmetros!");
      System.exit(1);
    }

    final String csvPartidos = args[0];
    final String csvCandidatos = args[1];
    final String dataString = args[2];

    String[] dadosData = dataString.split("/");
    int ano = Integer.parseInt(dadosData[2]);
    int mes = Integer.parseInt(dadosData[1]);
    int dia = Integer.parseInt(dadosData[0]);

    Calendar dataEleicao = Calendar.getInstance();
    dataEleicao.set(ano, mes, dia);

    Relatorios relatorios = new Relatorios(csvPartidos, csvCandidatos, dataEleicao);

    System.out.println(args[0]);

    // relatorios.relatorio1();
    // relatorios.relatorio2();
    // relatorios.relatorio3();
    // relatorios.relatorio4();
    // relatorios.relatorio5();
    // relatorios.relatorio6();
    // relatorios.relatorio7();
    // relatorios.relatorio8();
    relatorios.relatorio9();
    // relatorios.relatorio10();
    // System.out.println();
    // relatorios.relatorio11();
  }
}
