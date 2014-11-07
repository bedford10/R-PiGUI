

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MulticastServer 
{
	RxThread rxThread;
	TxThread txThread;
	
	public MulticastServer(Layer[] layers) 
	{
	
		DatagramSocket socket  = null;
		InetAddress    address = null;
		int            port    = 2000;
		String         host    = "10.0.0.255";
		//String         host    = "169.254.153.85";
		
		System.out.println("Multicast Server Starting...");
		
		try 
		{
			socket = new DatagramSocket(port);
			address = InetAddress.getByName(host);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			System.exit(1);
		}
		
		rxThread = new RxThread(socket, layers);
		txThread = new TxThread(socket, address, port);
		rxThread.start();
		txThread.start();
	}
	
	public Layer[] getUpdates()
	{
		return rxThread.getLayerUpdates();
	}
	
	public void setTxMessage(String msg)
	{
		txThread.setNextMessage(msg);
		return;
	}
	
	public void setUpdatedFalse()
	{
		rxThread.setUpdated();
	}
}
