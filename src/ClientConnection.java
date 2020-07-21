import java.net.Socket;

public class ClientConnection extends Thread{
	private Socket serverSock;
	private String servAdr;
	private int port;
	private BallTask bt;

	public ClientConnection(BallTask b) {
		this.bt=b;
		this.port=0;
		this.servAdr="localhost";
	}

	public Socket getSocket() {
		return serverSock;
	}
	public void reset() {
		this.serverSock=null;
	}
	public void setPort(int p) {
		this.port=p;
		System.out.println("Client target port: "+p);
	}
	
	public void run() {
		while (true) {
			try {
				sleep(5000);				
				if(!bt.rbtActive()) {
					this.serverSock= new Socket(servAdr,port);
					System.out.println("Client: "+serverSock);
					bt.setRemoteBallTaskSocket(serverSock);
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
}
