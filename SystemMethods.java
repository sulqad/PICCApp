package mrcards;

import javax.smartcardio.*;
import java.util.*;
import java.io.*;

public class SystemMethods{
	
	public static class KeyLoadException extends Exception{}
	public static class KeyAuthException extends Exception{}
	public static class InstructionFailedException extends Exception{}
	static CardTerminal terminal;
	static Card card;
	static CardChannel channel;
	static TerminalFactory factory;
	static List<CardTerminal> terminals;
	static ResponseAPDU answer;
	
	public static boolean connectTerminal(){
		
		try{
			factory =TerminalFactory.getDefault();
			terminals =factory.terminals().list();
			terminal =terminals.get(0);
		}
		catch(CardException e){
			return false;
		}
		return true;
	}
	
	public static void connectCard() throws CardException, NullPointerException{
		
		terminal.waitForCardPresent(0);
		card =terminal.connect("*");
		channel =card.getBasicChannel();
	}
	
	public static void loadKey(String key) throws NullPointerException, CardException, KeyLoadException{
		
		byte[] loadKeyCommand =Utilities.toByteArray("FF 82 00 20 06 " + key);
		answer =channel.transmit(new CommandAPDU(loadKeyCommand));
		if(answer.getSW() != 0x9000){
			card.disconnect(false);
			throw new KeyLoadException();
		}
	}
	
	public static void authenticateKey(int blockAddress, String keyType) throws CardException, KeyAuthException{
		int block =blockAddress;
		byte[] authenticateCommand =Utilities.toByteArray("FF 86 00 00 05 01 00 00 60 20");
		authenticateCommand[7] =(byte)block;
		
		switch(keyType){
			case "A": authenticateCommand[8] =(byte)0x60; break;
			case "B": authenticateCommand[8] =(byte)0x61; break;
			default: throw new KeyAuthException();
		}
		answer =channel.transmit(new CommandAPDU(authenticateCommand));
		
		if(answer.getSW() != 0x9000)
		{
			throw new KeyAuthException();
		}
	}
	
	public static void disconnectCard() throws CardException{
		
		card.disconnect(false);
		terminal.waitForCardAbsent(0);
	}
	
	public static String readData(int blockAddress) throws CardException, InstructionFailedException {
		
		String data ="Null";
		int block =blockAddress;
		byte[] readDataCommand =Utilities.toByteArray("FF B0 00 00 10");
		readDataCommand[3] =(byte)block;
		
		answer =channel.transmit(new CommandAPDU(readDataCommand));
		if(answer.getSW() != 0x9000)
		{
			card.disconnect(false);
			throw new InstructionFailedException();
		}
		else data = Utilities.hexify(answer.getData());
		return data;
	}
	
	public static void updateData(int blockAddress, String data) throws CardException, InstructionFailedException {
		
		int block =blockAddress;
		byte[] updateBinaryCommand =Utilities.toByteArray("FF D6 00 00 10 " +data);
		updateBinaryCommand[3] =(byte)block;
		
		answer =channel.transmit(new CommandAPDU(updateBinaryCommand));
		if(answer.getSW() != 0x9000){
			card.disconnect(false);
			throw new InstructionFailedException();
		}
	}
}