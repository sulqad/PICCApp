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
		
		t =new Thread(this, "Поток \"Прочитать данные\"");
		this.key =key;
		this.bAddress =bAddress;
		this.keyType =keyType;
		this.display =display;
		t.start();
	}
	
	public void run(){
		
		try{
			display.appendText("Приложите карту...\n");
			SystemMethods.connectCard();
			SystemMethods.authenticateKey(bAddress, keyType);
			display.appendText(SystemMethods.readData(bAddress) +"\n");
			display.appendText("Уберите карту...\n");
			SystemMethods.disconnectCard();
		}
		catch(CardException e){display.appendText("Ошибка чтения карты.\n" +e.toString() +"\n" +e.getMessage() +"\n" +e.getCause());}
		catch(NullPointerException e){display.appendText("Карта не подходит.\n");}
		catch(KeyAuthException e){display.appendText("Этот ключ\\тип ключа не подходит.\n");}
		catch(InstructionFailedException e){display.appendText("Этот тип ключа не может читать.\n");}
		catch(NumberFormatException e){display.appendText("Неверный формат ключа.\n");}
	}
}