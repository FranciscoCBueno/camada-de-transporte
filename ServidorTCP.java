import java.io.*;
import java.net.*;

public class ServidorTCP {
    public static void main(String[] args) {
        int porta = 12345; // Porta padrão do servidor

        // Para garantir que o ServerSocket seja fechado corretamente
        try (ServerSocket servidor = new ServerSocket(porta)) {
            System.out.println("Servidor TCP iniciado na porta " + porta);
            System.out.println("Aguardando conexões...");

            // Loop para aceitar múltiplas conexões de clientes
            while (true) {

                // Aceita uma conexão de um cliente
                Socket cliente = servidor.accept();
                InetAddress enderecoCliente = cliente.getInetAddress();
                System.out.println("Cliente conectado: " + enderecoCliente.getHostAddress());

                // Processa o cliente em uma nova thread
                try (
                        BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                        PrintWriter saida = new PrintWriter(cliente.getOutputStream(), true)) {
                    String mensagem;

                    // Loop para receber mensagens do cliente
                    while ((mensagem = entrada.readLine()) != null) {
                        System.out.println("Mensagem recebida do cliente: " + mensagem);

                        // Verifica se a mensagem é "sair"
                        if (mensagem.equalsIgnoreCase("sair")) {
                            saida.println("Conexão encerrada. Até logo!");
                            break;
                        }
                        saida.println("Mensagem recebida: " + mensagem);
                    }
                } catch (IOException e) {
                    // Tratamento de erros conexão com o cliente
                    System.err.println("Erro ao processar a conexão com o cliente: " + e.getMessage());
                } finally {
                    // Fecha o Socket do cliente
                    cliente.close();
                    System.out.println("Conexão com o cliente encerrada.\n");
                }
                System.out.println("Conexão com o cliente encerrada.\n");
            }
        } catch (IOException e) {
            // Tratamento de erros do servidor
            System.err.println("Erro no servidor: " + e.getMessage());
        }
    }
}