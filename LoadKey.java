package mrcards;

import javafx.scene.control.TextArea;
import mrcards.SystemMethods.*;
import javax.smartcardio.*;

class LoadKey implements Runnable{
	
	Thread t;
	String key;
	TextArea display;
	
	LoadKey(String key, TextArea display){
		
		t =new Thread(this, "����� \"�������� �����\"");
		this.key =key;
		this.display =display;
		t.start();
	}
	
	public void run(){
		
		try{
			display.appendText("��������� �����...\n");
			SystemMethods.connectCard();
			SystemMethods.loadKey(key);
			display.appendText("���� ��������\n");
			SystemMethods.disconnectCard();
		}
		catch(CardException e){display.appendText("������ ��� �������� �����.\n" +e.toString() +"\n" +e.getMessage() +"\n" +e.getCause());}
		catch(NullPointerException e){display.appendText("����� �� ��������.\n");}
		catch(NumberFormatException e){display.appendText("�������� ������ �����.\n");}
		catch(KeyLoadException e){display.appendText("���� �� ��������.\n");}
	}
}