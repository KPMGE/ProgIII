public class Main {
  public static void main(String[] args) {
    Relatorios relatorios = new Relatorios("./input/partidos.csv", "./input/candidatos.csv");

    relatorios.relatorio1();
    relatorios.relatorio2();
    relatorios.relatorio3();
    relatorios.relatorio6();
    System.out.println();
    relatorios.relatorio11();
  }
}
