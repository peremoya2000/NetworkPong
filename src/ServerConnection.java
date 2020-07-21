import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConnection extends Thread {
	private Socket clientSock;
	private ServerSocket serverSocket;
	private BallTask bt;

	public ServerConnection(BallTask b) {
		this.bt=b;
	}

	public Socket getSocket() {
		return clientSock;
	}
	public void reset() {
		this.serverSocket=null;
		this.clientSock=null;
	}
	
	public void openServer() {
		int p;
		
		// Mode 1
		try {
			p = 666;
			this.serverSocket = new ServerSocket(p);
			// Setejar client a 667
			bt.setClientPort(667);
			return; // ===============================================>
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		// Mode 2
		try {
			p = 667;
			this.serverSocket = new ServerSocket(p);
			// setejar port client a 666
			bt.setClientPort(666);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void accept() {
        System.out.println("Waiting for a client...");
	    try {
			clientSock = serverSocket.accept();
		} catch (IOException e) {
			System.out.println("Accept failed");
			try {
				serverSocket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			serverSocket=null;
		}
	    System.out.println("Server: "+clientSock);
		bt.setRemoteBallTaskSocket(clientSock);
	}
	
	public void run() {		
		try {
		    while (true) {
		    	if (serverSocket==null) {
		    		openServer();
		    	}else {
			    	if(!bt.rbtActive()) {
			    		accept();
			    	}else {
			    		sleep(500);
			    	}
		    	}
		     }
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
}
