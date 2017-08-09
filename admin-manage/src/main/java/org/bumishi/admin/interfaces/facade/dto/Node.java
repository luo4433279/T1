package org.bumishi.admin.interfaces.facade.dto;

public class Node implements java.io.Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2171131534722611908L;
	String name;
	String address;
	int value;
	byte[] buf;
	
	public Node()
	{
		name = "张三";
		address = "ww.cdessasfdsa";
		buf = new byte[50];
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public int getValue()
	{
		return value;
	}

	public void setValue(int value)
	{
		this.value = value;
	}

	public byte[] getBuf()
	{
		return buf;
	}

	public void setBuf(byte[] buf)
	{
		this.buf = buf;
	}
}
