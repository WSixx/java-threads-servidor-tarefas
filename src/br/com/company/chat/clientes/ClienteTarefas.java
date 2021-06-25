package br.com.company.chat.clientes;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClienteTarefas {

    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket("localhost", 3320);

        System.out.println("conexão estabelecida");

        Thread threadEnviaComando = new Thread(new Runnable() {
            @Override
            public void run() {
                PrintStream saida = null;
                try {
                    saida = new PrintStream(socket.getOutputStream());
                    Scanner teclado = new Scanner(System.in);
                    while (teclado.hasNextLine()) {
                        String linha = teclado.nextLine();

                        if (linha.trim().equals("")) {
                            break;
                        }
                        saida.println(linha);
                    }
                    saida.close();
                    teclado.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread threadRecebeResposta = new Thread(new Runnable() {
            @Override
            public void run() {

                System.out.println("Recebendo Dados do Servidor");
                try {
                    Scanner respostaServidor = new Scanner(socket.getInputStream());
                    while (respostaServidor.hasNextLine()) {
                        String linha = respostaServidor.nextLine();
                        System.out.println(linha);
                    }
                    respostaServidor.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        threadRecebeResposta.start();
        threadEnviaComando.start();

        threadEnviaComando.join();

        socket.close();
    }
}
