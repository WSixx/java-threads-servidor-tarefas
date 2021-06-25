package br.com.company.chat.servidor;

import java.io.IOException;
import java.lang.reflect.Executable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Servidor {

    public static void main(String[] args) throws IOException, InterruptedException {

        System.out.println("Iniciando Servidor");
        ServerSocket servidor = new ServerSocket(3320);
        ExecutorService threadPool = Executors.newCachedThreadPool();

        while (true){
            Socket socket = servidor.accept();
            System.out.println("Aceitando novo Cliente na porta: " + socket.getPort());

            DistribuirTarefas distribuirTarefas = new DistribuirTarefas(socket);
            threadPool.execute(distribuirTarefas);


        }

    }
}
