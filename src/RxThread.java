package temp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class RxThread extends Thread {

	public RxThread(DatagramSocket socket){
		super("RxThread");
		this.socket  = socket;
	}
	
	@Override
	public void run(){
		while(true){
			try{
				byte[] buf = new byte[256];
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);
				
				// Print the received packet
				String msg = packet.getAddress().getHostAddress().toString() + ": ";
				msg += new String(packet.getData());
				System.out.println(msg);				
			}catch(IOException e){
				e.printStackTrace();
				break;
			}
		}
		socket.close();
	}
	
	private DatagramSocket socket  = null;
	
}
