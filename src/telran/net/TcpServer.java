package telran.net;
import java.io.IOException;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class TcpServer implements Runnable {
private static final int DEFAULT_THREADS_POOL_CAPACITY = 3;
private int port;
private ApplProtocol protocol;
private ServerSocket serverSocket;
private ExecutorService executor;

public TcpServer(int port, ApplProtocol protocol, int nThreads) throws Exception{
	this.port = port;
	this.protocol = protocol;
	serverSocket = new ServerSocket(port);
	executor = Executors.newFixedThreadPool(nThreads);
	
}
public TcpServer(int port, ApplProtocol protocol) throws Exception{
	this(port, protocol, DEFAULT_THREADS_POOL_CAPACITY);
	
}
	@Override
	public void run() {
		System.out.println("Server is listening on the port " + port);
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				TcpClientServer client = new TcpClientServer(socket, protocol);
				Thread threadClient = new Thread(client);
				executor.execute(threadClient);
			} catch (Exception e) {
				
				e.printStackTrace();
				break;
			}
		}

	}

}
