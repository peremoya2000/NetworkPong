import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RemoteBallTask implements Runnable {
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private Thread listener;
	private BallProtocol protocol;
	private BallTask bt;
	private boolean listening;

	public RemoteBallTask(BallTask bt) {
		this.bt = bt;
		listening = false;
		protocol = new BallProtocol(bt);
		socket = null;
		this.listener = new Thread(this);
	}

	public synchronized void setSocket(Socket s) {
		if (this.socket != null) {
			try {
				s.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return;
		}

		this.socket = s;
		try {
			this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.out = new PrintWriter(socket.getOutputStream(), true);// autoflush
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("RBT: Socket set");
		System.out.println(socket);
		if (!listening)
			listener.start();

		listening = true;
		ping();
	}

	public synchronized boolean hasSocket() {
		return this.socket != null;
	}

	public void ping() {
		if (!this.hasSocket()) {
			System.out.println("Ping failed");
			return;
		}
		out.println("Hola");
	}

	@Override
	public void run() {
		String message;
		System.out.println("Listening");
		while (true) {
			try {
				if (!this.hasSocket()) {
					System.out.println("Error");
					return;
				}
				message = in.readLine();
				System.out.println("Bolla rebuda " + socket);
				if (!message.equals("Hola") && message != null) {
					if(message.equals("PointScored")) {
						bt.getScore().incrementScore();
					}else {
						protocol.createBall(message);
					}
				}	

			} catch (IOException e) {
				System.out.println(e.getMessage());
				closeSocket();
			}
		}
	}

	public synchronized void closeSocket() {
		try {
			this.socket.close();
			this.socket = null;
			this.in = null;
			this.out = null;
			bt.resetConnections();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public synchronized void sendBall(Ball b) {
		try {
			System.out.println("Bolla eviada " + socket);
			out.println(protocol.sendBall(b));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			closeSocket();
		}
	}
	
	public synchronized void pointScored() {
		try {
			out.println("PointScored");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			closeSocket();
		}
	}
}
