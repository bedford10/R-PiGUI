import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class RxThread extends Thread 
{
	
	private DatagramSocket socket  = null;
	private Layer[] layers = new Layer[2];
	
	public RxThread(DatagramSocket socket, Layer[] _layers)
	{
		super("RxThread");
		this.socket  = socket;
		layers = _layers;
	}
	
	@Override
	public void run()
	{
		while(true)
		{
			try
			{
				byte[] buf = new byte[256];
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);
				
				// Print the received packet
				parseMessage(packet);
				String msg = packet.getAddress().getHostAddress().toString() + ": ";
				msg += new String(packet.getData());
				System.out.println(msg);				
			}
			catch(IOException e)
			{
				e.printStackTrace();
				break;
			}
		}
		socket.close();
	}
	
	private void parseMessage(DatagramPacket packet) 
	{
		String ipAddress = packet.getAddress().getHostAddress().toString();
		String message = new String(packet.getData());
		
		String[] nodeInfo = ipAddress.split(".");
		String[] msgInfo = message.split(":");
		
		int row = Integer.parseInt(nodeInfo[2]);
		int column = Integer.parseInt(nodeInfo[3]);
		int layerNumber = Integer.parseInt(nodeInfo[1]);
		float temp = Float.parseFloat(msgInfo[1]);
		
		if("temp".equals(msgInfo[0]))
		{
			layers[layerNumber].getNode(row*column).setNodeTemp(temp);
			layers[layerNumber].getNode(row*column).setUpdated(true);
		}
	}

	public Layer[] getLayerUpdates()
	{
		return layers;
	}
	
	public void setUpdated()
	{
		for(int i = 0; i < 32; i++)
		{
			if(i < 16)
				layers[0].getNode(i).setUpdated(false);
			else
				layers[1].getNode(i).setUpdated(false);
		}
	}
}
