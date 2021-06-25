package br.com.company.chat.servidor;

import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class DistribuirTarefas implements Runnable {
    private Socket socket;

    public DistribuirTarefas(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            System.out.println("Distribuindo Tarefas: " + socket);

            Scanner entradaCliente = new Scanner(socket.getInputStream());
            PrintStream saidaCliente = new PrintStream(socket.getOutputStream());

            while (entradaCliente.hasNextLine()) {
                String comando = entradaCliente.nextLine();
                System.out.println("Comando recebido: " + comando.trim());


                switch (comando.trim()) {
                    case "c1":
                        saidaCliente.println("Confirmação comando C1");
                        break;
                    case "c2":
                        saidaCliente.println("Confirmação comando C2");
                        break;
                    default:
                        saidaCliente.println("Comando não enocntrado");

                        break;
                }

                System.out.println(comando);
            }
            saidaCliente.close();
            entradaCliente.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
