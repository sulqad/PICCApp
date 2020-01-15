package mrcards;

import javafx.scene.control.TextArea;
import mrcards.SystemMethods.*;
import javax.smartcardio.*;

class LoadKey implements Runnable{
	
	Thread t;
	String key;
	TextArea display;
	
	LoadKey(String key, TextArea display){
		
		t =new Thread(this, "Поток \"Загрузка ключа\"");
		this.key =key;
		this.display =display;
		t.start();
	}
	
	public void run(){
		
		try{
			display.appendText("Приложите карту...\n");
			SystemMethods.connectCard();
			SystemMethods.loadKey(key);
			display.appendText("Ключ загружен\n");
			SystemMethods.disconnectCard();
		}
		catch(CardException e){display.appendText("Ошибка при загрузке ключа.\n" +e.toString() +"\n" +e.getMessage() +"\n" +e.getCause());}
		catch(NullPointerException e){display.appendText("Карта не подходит.\n");}
		catch(NumberFormatException e){display.appendText("Неверный формат ключа.\n");}
		catch(KeyLoadException e){display.appendText("Ключ не подходит.\n");}
	}
}