
public class Node 
{
	private float nodeTemp;
	boolean updated = false;
	
	public Node()
	{
		setNodeTemp(60);
	}
	
	public float getNodeTemp()
	{
		return nodeTemp;
	}
	public void setNodeTemp(float temp)
	{
		nodeTemp = temp;
	}
	
	public boolean getUpdated()
	{
		return updated;
	}
	
	public void setUpdated(boolean status)
	{
		updated = status;
	}
}
