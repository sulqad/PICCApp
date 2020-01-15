package mrcards;

import java.util.ArrayList;
import java.lang.StringIndexOutOfBoundsException;

import static java.util.Arrays.copyOfRange;

public class Utilities
{
	public static String hexify(byte[] bytes)
	{
		ArrayList<String> bytesToString =new ArrayList<String>(bytes.length);
		for(byte b: bytes)
		{
			bytesToString.add(String.format("%02X", b));
		}
		return String.join(" ", bytesToString);
	}
	
	public static byte[] toByteArray(String s)
	{
		int len =s.length();
		byte[] buf =new byte[len/2];
		int bufLen =0;
		int i =0;
		
		try
		{
		while(i <len)
		{
			char c1 =s.charAt(i);
			i++;
			if(c1 ==' ') continue;
			
			char c2 =s.charAt(i);
			i++;
			
			byte d =(byte)((Character.digit(c1, 16)<<4) + (Character.digit(c2, 16)));
			buf[bufLen] =d;
			++bufLen;
		}
		}
		catch(StringIndexOutOfBoundsException e){}
		return copyOfRange(buf, 0, bufLen);
	}
}