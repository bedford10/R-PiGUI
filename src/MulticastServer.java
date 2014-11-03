package temp;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MulticastServer {

	public static void main(String[] args) {
	
		DatagramSocket socket  = null;
		InetAddress    address = null;
		int            port    = 2000;
		String         host    = "10.0.0.255";
		
		System.out.println("Multicast Server");
		
		try {
			socket = new DatagramSocket(port);
			address = InetAddress.getByName(host);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		new RxThread(socket).start();
		new TxThread(socket, address, port).start();
		
		while(true){}


	}

}
