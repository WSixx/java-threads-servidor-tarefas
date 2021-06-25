package br.com.company.chat.servidor;

import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class DistribuirTarefas implements Runnable {
    private Socket socket;
    private Servidor servidor;

    public DistribuirTarefas(Socket socket, Servidor servidor) {
        this.socket = socket;
        this.servidor = servidor;
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
                        ComandoC1 c1 = new ComandoC1(saidaCliente);
                        break;
                    case "c2":
                        saidaCliente.println("Confirmação comando C2");
                        break;
                    case "fim":
                        saidaCliente.println("Desligando Servidor");
                        servidor.parar();
                        break;
                    default:
                        saidaCliente.println("Comando não enocntrado");
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
