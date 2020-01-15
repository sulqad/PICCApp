package mrcards;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import mrcards.SystemMethods.*;
import javax.smartcardio.*;

class UpdateData implements Runnable{
	
	Thread t;
	String key;
	String keyType;
	int bAddress;
	TextArea display;
	TextField setData;
	
	UpdateData(String key, int bAddress, String keyType, TextArea display, TextField setData){
		
		t =new Thread(this, "����� \"��������\"");
		this.key =key;
		this.bAddress =bAddress;
		this.keyType =keyType;
		this.display =display;
		this.setData =setData;
		t.start();
	}
	
	public void run(){
		
		try{
			display.appendText("��������� �����...\n");
			SystemMethods.connectCard();
			SystemMethods.authenticateKey(bAddress, keyType);
			SystemMethods.updateData(bAddress, setData.getText());
			display.appendText("��������. ������� �����.\n");
			SystemMethods.disconnectCard();
		}
		catch(CardException e){display.appendText("������ ������� \"���������\".\n" +e.toString() +"\n" +e.getMessage() +"\n" +e.getCause());}
		catch(NullPointerException e){display.appendText("��� ���������� �����.\n");}
		catch(KeyAuthException e){display.appendText("���� ����\\��� ����� �� ��������.\n");}
		catch(NumberFormatException e){display.appendText("������: ������������ ��� ������.\n");}
		catch(InstructionFailedException e){display.appendText("�� ������� ������ ������. ��������� ��� � ������ ������.\n");}
	}
}