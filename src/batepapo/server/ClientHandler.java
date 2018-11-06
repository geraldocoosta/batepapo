package batepapo.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientHandler implements Runnable {
	private Socket sock;
	private BufferedReader reader;
	Server server;

	public ClientHandler(Socket clientSocket, Server server) {
		this.server = server;
		try {
			sock = clientSocket;
			InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
			reader = new BufferedReader(isReader);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		String messages;
		try {
			System.out.println("Entrou no try");
			while ((messages = reader.readLine()) != null) {
				System.out.println("read " + messages);
				server.tellEveryone(messages);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}