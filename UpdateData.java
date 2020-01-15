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
		
		t =new Thread(this, "Поток \"Записать\"");
		this.key =key;
		this.bAddress =bAddress;
		this.keyType =keyType;
		this.display =display;
		this.setData =setData;
		t.start();
	}
	
	public void run(){
		
		try{
			display.appendText("Приложите карту...\n");
			SystemMethods.connectCard();
			SystemMethods.authenticateKey(bAddress, keyType);
			SystemMethods.updateData(bAddress, setData.getText());
			display.appendText("Записано. Уберите карту.\n");
			SystemMethods.disconnectCard();
		}
		catch(CardException e){display.appendText("Ошибка команды \"Прочитать\".\n" +e.toString() +"\n" +e.getMessage() +"\n" +e.getCause());}
		catch(NullPointerException e){display.appendText("Нет подходящей карты.\n");}
		catch(KeyAuthException e){display.appendText("Этот ключ\\тип ключа не подходит.\n");}
		catch(NumberFormatException e){display.appendText("Ошибка: неправильный тип данных.\n");}
		catch(InstructionFailedException e){display.appendText("Не удалось внести запись. Проверьте тип и формат данных.\n");}
	}
}