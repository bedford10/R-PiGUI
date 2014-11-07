import java.net.DatagramPacket;


public class ParseThread extends Thread
{
	private Layer[] layers = new Layer[2];
	DatagramPacket message;
	
	public void run(DatagramPacket message)
	{
		parseMessage(message);
	}
	
	
	public Layer[] getLayers() 
	{
		return layers;
	}
	
	public void setLayers(Layer[] layers) 
	{
		this.layers = layers;
	}
	
	public void setMessage(DatagramPacket msg)
	{
		message = msg;
	}
	
	public void setUpdated()
	{
		for(int i = 0; i < 32; i++)
		{
			if(i < 16)
				layers[0].getNode(i).setUpdated(false);
			else
				layers[1].getNode(i-16).setUpdated(false);
		}
	}
	
	private void parseMessage(DatagramPacket packet) 
	{
		String ipAddress = packet.getAddress().getHostAddress().toString();
		String message = new String(packet.getData());
		
		String[] nodeInfo = ipAddress.split(".");
		String[] msgInfo = message.split(":");
		
		int row = Integer.parseInt(nodeInfo[2]) - 101;
		int column = Integer.parseInt(nodeInfo[3]) - 101;
		int layerNumber = Integer.parseInt(nodeInfo[1]) - 115;
		float temp = Float.parseFloat(msgInfo[1]);
		
		if("temp".equals(msgInfo[0]))
		{
			layers[layerNumber].getNode(row*column).setNodeTemp(temp);
			layers[layerNumber].getNode(row*column).setUpdated(true);
		}
	}
	
}
