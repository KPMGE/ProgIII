import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ListaPartidos {
  private String caminhoCsv;
  private ArrayList<Partido> partidos = new ArrayList<Partido>();

  public ListaPartidos(String caminhoCsv) {
    this.caminhoCsv = caminhoCsv;
  }

  public String getCaminhoCsv() {
    return this.caminhoCsv;
  }

  public ArrayList<Partido> getListaPartidos() {
    return this.partidos;
  }

  public void exibePartidos() {
    System.out.println("Partidos lidos:\n");
    for (Partido partido : this.partidos) {
      System.out.println("Nome: " + partido.getNome());
      System.out.println("Numero: " + partido.getNumero());
      System.out.println("sigla: " + partido.getSigla());
      System.out.println("Votos Legenda: " + partido.getVotosLegenda());
      System.out.println();
    }
  }

  public void lePartidos() {
    try {
      File csv = new File(this.caminhoCsv);
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
        this.partidos.add(novoPartido);
      }

      reader.close();
    } catch (FileNotFoundException ex) {
      System.out.println("Arquivo não encontrado!");
      ex.printStackTrace();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}