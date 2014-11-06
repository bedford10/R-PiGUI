import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class TxThread extends Thread {

	DatagramSocket socket = null;
	int port = 0;
	InetAddress address = null;
	String message = "";
	
	public TxThread(DatagramSocket socket, InetAddress address, int port)
	{
		super("TxThread");
		this.socket = socket;
		this.address = address;
		this.port = port;
	}
	
	@Override
	public void run()
	{
		while(true)
		{
			if(!"".equals(message))
			{
				String tmp = message;
				DatagramPacket packet = new DatagramPacket(tmp.getBytes(), tmp.length(), address, port);
				try 
				{
					socket.send(packet);
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
					break;
				}
				message = "";
			}
		}
	}
	
	public void setNextMessage (String msg)
	{
		message = msg;
	}
}
