package temp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class TxThread extends Thread {

	public TxThread(DatagramSocket socket, InetAddress address, int port){
		super("TxThread");
		this.socket  = socket;
		this.address = address;
		this.port    = port;
	}
	
	@Override
	public void run(){
		while(true){
			if(input.hasNextLine()){
				String         tmp    = input.nextLine();
				DatagramPacket packet = new DatagramPacket(tmp.getBytes(), tmp.length(), address, port);
				try {
					socket.send(packet);
				} catch (IOException e) {
					e.printStackTrace();
					break;
				}
			}
		}
	}
	
	DatagramSocket socket  = null;
	int            port    = 0;
	Scanner        input   = new Scanner(System.in);
	InetAddress    address = null;
	
	
}
