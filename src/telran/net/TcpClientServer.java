package telran.net;
import java.io.*;
import java.net.*;

import telran.net.dto.*;
public class TcpClientServer implements Runnable {
Socket socket;
ObjectInputStream reader;
ObjectOutputStream writer;
ApplProtocol protocol;
public TcpClientServer(Socket socket, ApplProtocol protocol) throws Exception{
	this.socket = socket;
	
	//socket.setSoTimeout(1000); //if socket is in the idle mode after 1 sec.
	//there will be SocketTimeoutException
	reader = new ObjectInputStream(socket.getInputStream());
	writer = new ObjectOutputStream(socket.getOutputStream());
	this.protocol = protocol;
}
	@Override
	public void run() {
			while(!TcpServer.isShutdown) {
				try {
					Request request = (Request) reader.readObject();
					Response response = protocol.getResponse(request);
					writer.writeObject(response);
				} catch (SocketTimeoutException e) {
					
				}
				catch(EOFException e) {
					break;
				} catch (Exception e) {
					System.out.println(e);
					break;
				}
			}
			try {
				socket.close();
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
		

	}

}
