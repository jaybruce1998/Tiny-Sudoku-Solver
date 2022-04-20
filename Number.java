import java.util.ArrayList;
public class Number
{
	private ArrayList<Integer> possibilities;
	private int value;
	public Number(int v)
	{
		possibilities=new ArrayList<>();
		value=v;
		for(int i=1; i<10; i++)
			possibilities.add(i);
	}
	public Number(Number n)
	{
		possibilities=new ArrayList<>();
		value=n.value();
		for(int i=0; i<n.pSize(); i++)
			possibilities.add(n.pGet(i));
	}
	public boolean valid()
	{
		return possibilities.size()>0||value>0;
	}
	public boolean remove(Integer n)
	{
		return possibilities.remove(n);
	}
	public int value()
	{
		return value;
	}
	public int pSize()
	{
		return possibilities.size();
	}
	public int pGet(int i)
	{
		return possibilities.get(i);
	}
	public boolean shouldSet()
	{
		return value==0&&possibilities.size()==1;
	}
	public int set()
	{
		value=possibilities.get(0);
		return value;
	}
	public boolean unresolved()
	{
		return value==0&&possibilities.size()>1;
	}
	public String toString()
	{
		if(value>0)
			return value+"";
		return " ";
	}
}
