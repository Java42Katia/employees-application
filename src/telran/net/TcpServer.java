package telran.net;
import java.io.IOException;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
public class TcpServer implements Runnable {
private static final int DEFAULT_THREADS_POOL_CAPACITY = 3;
private static final int TIMEOUT = 2000;
private int port;
private ApplProtocol protocol;
private ServerSocket serverSocket;
private ExecutorService executor;
static boolean isShutdown = false;
static long timeout;

public TcpServer(int port, ApplProtocol protocol, int nThreads) throws Exception{
	this.port = port;
	this.protocol = protocol;
	serverSocket = new ServerSocket(port);
	serverSocket.setSoTimeout(TIMEOUT);
	executor = Executors.newFixedThreadPool(nThreads);
	
}
public TcpServer(int port, ApplProtocol protocol) throws Exception{
	this(port, protocol, DEFAULT_THREADS_POOL_CAPACITY);
	
}
	@Override
	public void run() {
		System.out.println("Server is listening on the port " + port);
		while (!isShutdown) {
			try {
				Socket socket = serverSocket.accept();
				socket.setSoTimeout(TIMEOUT);
				TcpClientServer client = new TcpClientServer(socket, protocol);
				
				executor.execute(client);
			} catch (SocketTimeoutException e) {
				
			}
			catch (Exception e) {
				
				e.printStackTrace();
				break;
			}
		}
		

	}
	public void shutdown(int timeout) {
		//solution of a graceful shutdown
		//What is a graceful server shutdown
		//1. No receive new clients
		//2. Running getResponse should be performed
		//3. After SocketTimeoutException exiting from the loop for each client (to trigger SocketTimeoutException
		// you should apply method setSoTimeout for the client sockets with the given timeout)
		
		executor.shutdownNow();
		try {
			executor.awaitTermination(timeout, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e1) {
			
			e1.printStackTrace();
		}
		isShutdown = true;
		
	}

}
