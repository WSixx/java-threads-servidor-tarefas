package br.com.company.chat.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class Servidor {

    private final ServerSocket servidor;
    private final ExecutorService threadPool;
    private AtomicBoolean estaRodando;

    public Servidor() throws IOException {
        System.out.println("Iniciando Servidor");
        this.servidor = new ServerSocket(3320);
        this.threadPool = Executors.newCachedThreadPool();
        this.estaRodando = new AtomicBoolean(true);
    }

    public void parar() throws IOException {
        this.estaRodando.set(false);
        servidor.close();
        threadPool.shutdown();
    }

    public void rodar() throws IOException {
        while (this.estaRodando.get()) {
            try {
                Socket socket = servidor.accept();
                System.out.println("Aceitando novo Cliente na porta: " + socket.getPort());
                DistribuirTarefas distribuirTarefas = new DistribuirTarefas(socket, this);
                threadPool.execute(distribuirTarefas);
            } catch (SocketException socketException) {
                System.out.println("SocketException");
            }

        }
    }

    public static void main(String[] args) throws IOException {
        Servidor servidor = new Servidor();
        servidor.rodar();
        servidor.parar();
    }
}
