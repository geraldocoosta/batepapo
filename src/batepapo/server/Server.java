package batepapo.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Server {
	private List<PrintWriter> clientOutputStream;

	public void go() {
		clientOutputStream = new ArrayList<>();
		ServerSocket serverSock = null;
		try {
			serverSock = new ServerSocket(5000);
			while (true) {
				Socket clientSocket = serverSock.accept();
				PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
				clientOutputStream.add(writer);
				Thread t = new Thread(new ClientHandler(clientSocket,this));
				t.start();
				System.out.println("Conexão iniciada");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				serverSock.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void tellEveryone(String message) {
		Iterator<PrintWriter> iterator = clientOutputStream.iterator();
		while (iterator.hasNext()) {
			PrintWriter writer = iterator.next();
			writer.println(message);
			writer.flush();
		}
	}

	public static void main(String[] args) {
		new Server().go();
	}
}
