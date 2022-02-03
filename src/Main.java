import java.util.Calendar;
import java.util.ArrayList;
import java.util.Locale;

public class Main {
  public static void main(String[] args) {
    if (args.length < 3) {
      System.out.println("Erro! Passe todos os parâmetros!");
      System.exit(1);
    }

    // arquivos csv e data da eleição
    final String csvPartidos = args[0];
    final String csvCandidatos = args[1];
    final String dataString = args[2];

    // extrai os números a string dada como data
    String[] dadosData = dataString.split("/");
    int ano = Integer.parseInt(dadosData[2]);
    int mes = Integer.parseInt(dadosData[1]);
    int dia = Integer.parseInt(dadosData[0]);

    // cria um novo calendário, com a data da eleição passada
    Calendar dataEleicao = Calendar.getInstance();
    dataEleicao.set(ano, mes, dia);

    // lendo as listas de candidatos e de partidos.
    ArrayList<Partido> listaPartidos = Utilitarios.lePartidos(csvPartidos);
    ArrayList<Candidato> listaCandidatos = Utilitarios.leCandidatos(csvCandidatos);
    /*
     * relacionando as duas listas lidas, 1 candidato tem um, e somente um partido
     * associado, enquanto que,um partido tem uma lista de candidatos associados.
     */
    Utilitarios.relacionaPartidoComCandidatos(listaPartidos, listaCandidatos);

    Locale locale = new Locale("pt", "BR");    
    // define o Locale padrão da JVM
    Locale.setDefault(locale);


    // gerando os relatórios.
    Relatorios.relatorio1(listaCandidatos);
    Relatorios.relatorio2(listaCandidatos);
    Relatorios.relatorio3(listaCandidatos);
    Relatorios.relatorio4(listaCandidatos);
    Relatorios.relatorio5(listaCandidatos);
    Relatorios.relatorio6(listaPartidos);
    Relatorios.relatorio7(listaPartidos);
    Relatorios.relatorio8(listaPartidos);
    Relatorios.relatorio9(listaCandidatos, dataEleicao);
    Relatorios.relatorio10(listaCandidatos);
    Relatorios.relatorio11(listaPartidos);
  }
}
