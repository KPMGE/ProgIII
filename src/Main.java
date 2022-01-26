public class Main {
  public static void main(String[] args) {
    Relatorios relatorios = new Relatorios("./input/partidos.csv", "./input/candidatos.csv");

    relatorios.relatorio1();
    System.out.println();
    relatorios.relatorio2();
    System.out.println();
    relatorios.relatorio3();
  }
}
