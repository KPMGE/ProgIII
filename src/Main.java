public class Main {
  public static void main(String[] args) {
    /*
     * ListaPartidos partidos = new ListaPartidos("./input/partidos.csv");
     * partidos.lePartidos();
     * partidos.exibePartidos();
     */

    ListaVereadores vereadores = new ListaVereadores("./input/candidatos.csv");
    vereadores.leVereadores();
    vereadores.exibeVereadores();
  }
}
