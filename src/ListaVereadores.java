import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class ListaVereadores {
  private String caminhoCsv;
  private ArrayList<Vereador> vereadores = new ArrayList<Vereador>();

  public ListaVereadores(String caminhoCsv) {
    this.caminhoCsv = caminhoCsv;
  }

  public String getCaminhoCsv() {
    return caminhoCsv;
  }

  public ArrayList<Vereador> getVereadores() {
    return vereadores;
  }

  public void exibeVereadores() {
    for (Vereador vereador : this.vereadores) {
      System.out.println("Nome: " + vereador.getNome());
      System.out.println("Nome Urna: " + vereador.getNomeUrna());
      System.out.println("Numero: " + vereador.getNumero());
      System.out.println("Numero Partido: " + vereador.getNumeroPartido());
      System.out.println("Situcão: " + vereador.getSituacao());
      System.out.println("Sexo: " + vereador.getSexo());
      System.out.println("Votos Nominais: " + vereador.getVotosNominais());
      System.out.println("Destino Voto: " + vereador.getDestinoVoto());
      System.out.printf("Data dataNascimento: %tc\n", vereador.getDataNascimento());
      System.out.println();
    }
  }

  public void leVereadores() {
    try {
      File csv = new File(this.caminhoCsv);
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

        // System.out.println("numero: " + numero);
        // System.out.println("votos nominais: " + votosNominais);
        // System.out.println("situacao" + situacao);
        // System.out.println("nome" + nome);
        // System.out.println("nome urna" + nomeUrna);
        // System.out.println("sexo: " + sexo);
        // System.out.println("destino Voto: " + destinoVoto);
        // System.out.println("numero partido: " + numeroPartido);
        // System.out.println();

        // criando data
        Calendar dataNascimento = Calendar.getInstance();
        String[] dadosDataNascimento = dataNascimentoString.split("/");
        int dia = Integer.parseInt(dadosDataNascimento[0]);
        int mes = Integer.parseInt(dadosDataNascimento[1]);
        int ano = Integer.parseInt(dadosDataNascimento[2]);
        dataNascimento.set(ano, mes, dia);

        // criando novo vereador e salvando-o na lista
        Vereador novoVereador = new Vereador(numero, votosNominais, numeroPartido, situacao, nome, nomeUrna,
            destinoVoto, sexo, dataNascimento);

        vereadores.add(novoVereador);
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
