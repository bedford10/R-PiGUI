
public class Layer 
{
	private Node[] nodes;
	private int layerNumber;
	private int redCount;
	
	public Layer(int layerNo)
	{
		this.layerNumber = layerNo;
		nodes = new Node[16];
		init();
	}
	
	private void init()
	{
		for(int i = 0; i < 16; i++)
		{
			nodes[i] = new Node();
			if(layerNumber == 1 && (i % 2 == 0))
			{
				nodes[i].setNodeTemp(62);
			}
			else if(layerNumber == 1 && (i % 2 == 1))
			{
				nodes[i].setNodeTemp(999);
				redCount++;
			}
			else
			{
				nodes[i].setNodeTemp(60);
			}
		}
		
	}
	
	public int getLayerNumber()
	{
		return layerNumber;
	}
	
	public Node getNode(int nodeNo)
	{
		return nodes[nodeNo];
	}
	
	public double getAverageTemp()
	{
		double temp = 0;
		int offlineCount = 0;
		for(int i = 0; i < 16; i++)
		{
			if(getNode(i).getNodeTemp() != 999)
			{
				temp += getNode(i).getNodeTemp();
			}
			else
				offlineCount++;
		}
		temp = temp/(16-offlineCount);
		return temp;
	}
	
	public int getOfflineNodeCount()
	{
		redCount = 0;
		for(int i = 0; i < 16; i++)
		{
			if(nodes[i].getNodeTemp() == 999)
				redCount++;
		}
		return redCount;
	}
}
