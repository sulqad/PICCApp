package mrcards;

import javafx.scene.control.TextArea;
import mrcards.SystemMethods.*;
import javax.smartcardio.*;

class ReadData implements Runnable{
	
	Thread t;
	String key;
	String keyType;
	int bAddress;
	TextArea display;
	
	ReadData(String key, int bAddress, String keyType, TextArea display){
		
		t =new Thread(this, "����� \"��������� ������\"");
		this.key =key;
		this.bAddress =bAddress;
		this.keyType =keyType;
		this.display =display;
		t.start();
	}
	
	public void run(){
		
		try{
			display.appendText("��������� �����...\n");
			SystemMethods.connectCard();
			SystemMethods.authenticateKey(bAddress, keyType);
			display.appendText(SystemMethods.readData(bAddress) +"\n");
			display.appendText("������� �����...\n");
			SystemMethods.disconnectCard();
		}
		catch(CardException e){display.appendText("������ ������ �����.\n" +e.toString() +"\n" +e.getMessage() +"\n" +e.getCause());}
		catch(NullPointerException e){display.appendText("����� �� ��������.\n");}
		catch(KeyAuthException e){display.appendText("���� ����\\��� ����� �� ��������.\n");}
		catch(InstructionFailedException e){display.appendText("���� ��� ����� �� ����� ������.\n");}
		catch(NumberFormatException e){display.appendText("�������� ������ �����.\n");}
	}
}