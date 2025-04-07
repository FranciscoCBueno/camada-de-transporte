import java.io.*;
import java.net.*;

public class ClienteTCP {
    public static void main(String[] args) {
        String servidor = "localhost";// Endereço do servidor
        int porta = 12345;// Porta do servidor onde o servidor está escutando

        // Garante que os recursos sejam fechados corretamente
        try (
                Socket socket = new Socket(servidor, porta);
                BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
                BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter saida = new PrintWriter(socket.getOutputStream(), true)) {
            System.out.println("Conectado ao servidor em " + servidor + ":" + porta);
            String mensagem;

            // Loop para enviar mensagens ao servidor
            while (true) {
                System.out.print("Digite uma mensagem (ou 'sair' para encerrar): ");

                // Lê mensagem do usuário e envia ao servidor
                mensagem = teclado.readLine();
                saida.println(mensagem);

                // Lê e exibe a resposta do servidor
                String resposta = entrada.readLine();
                System.out.println("Servidor respondeu: " + resposta);

                // Verifica se a mensagem é "sair" para encerrar a conexão
                if (mensagem.equalsIgnoreCase("sair")) {
                    break;
                }
            }
        } catch (IOException e) {
            // Tratamento de erros de conexão com o servidor
            System.err.println("Erro no cliente: " + e.getMessage());
        }
    }
}