package br.com.company.chat.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    public static void main(String[] args) throws IOException {

        System.out.println("Iniciando Servidor");
        ServerSocket servidor = new ServerSocket(3320);

        while (true){
            Socket socket = servidor.accept();
            System.out.println("Aceitando novo Cliente na porta: " + socket.getPort());
        }

    }
}
