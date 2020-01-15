package mrcards;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import mrcards.SystemMethods.*;
import javax.smartcardio.*;

import java.util.*;
import java.io.*;

class Batch implements Runnable{
	
	Thread t;
	
	int blockAddress;
	int trailerAddress;
	String trailerData;
	TextArea batchDisplay;
	boolean stopBatch;
	String data ="";
	
	int idx =0;
	
	String keyType;
	
	Batch(int blockAddress, int trailerAddress, String keyType, String trailerData, TextArea batchDisplay){
		
		t =new Thread(this, "Поток \"Конвейер\"");

		this.blockAddress =blockAddress;
		this.trailerAddress =trailerAddress;
		this.keyType =keyType;
		this.trailerData =trailerData;
		this.batchDisplay =batchDisplay;
		this.stopBatch =stopBatch;
		
		t.start();
	}
	
	public void run(){
		
		try{
			
			Scanner initialString =new Scanner(new File("resources/log.txt"));
			String fromNextString =initialString.nextLine();
			
			Scanner inputData =new Scanner(new File("resources/sourceFile.txt"));
			if(inputData.hasNextLine())	inputData.findWithinHorizon(fromNextString, 0);
			
			
			while(inputData.hasNextLine()){
				
				try{
					SystemMethods.connectCard();
					SystemMethods.authenticateKey(blockAddress, keyType);
					
					FileWriter fw =new FileWriter(new File("resources/log.txt"));
					 data =inputData.nextLine();
					
					SystemMethods.updateData(blockAddress, data);
					
					fw.write(data +"\r\n");
					fw.close();
					
					SystemMethods.updateData(trailerAddress, trailerData);
					
					batchDisplay.appendText(data +" - Успешно");
					SystemMethods.disconnectCard();
					batchDisplay.clear();
					
				}
				catch(CardException e){
					batchDisplay.appendText("Ошибка конвейера:\n\n" +e.getCause() +"\n\n" +
											"\n\n" + e.toString() +"\n\n" +e.getMessage() +"\n");
					try{
						SystemMethods.disconnectCard();
					}
					catch(CardException exc){batchDisplay.appendText("Ошибка разоединения канала связи с картой.\n");}
				}
				catch(NullPointerException e){
					batchDisplay.appendText("Нет подходящей карты для соединения.\n");
					
					try{
						SystemMethods.disconnectCard();
					}
					catch(CardException exc){batchDisplay.appendText("Ошибка разоединения канала связи с картой.\n");}
				}
				catch(KeyAuthException e){
					batchDisplay.appendText("Этот ключ\\тип ключа не подходит\n");
					
					try{
						SystemMethods.disconnectCard();
					}
					catch(CardException exc){batchDisplay.appendText("Ошибка разоединения канала связи с картой.\n");}
				}
				catch(NumberFormatException e){
					batchDisplay.appendText("Ошибка: неправильный тип данных.\n");
					
					try{
						SystemMethods.disconnectCard();
					}
					catch(CardException exc){batchDisplay.appendText("Ошибка разоединения канала связи с картой.\n");}
				}
				catch(InstructionFailedException e){
					batchDisplay.appendText("Не удалось внести запись.\nПроверьте формат данных.\n");
					
					try{
						SystemMethods.disconnectCard();
					}
					catch(CardException exc){batchDisplay.appendText("Ошибка разоединения канала связи с картой.\n");}
				}
			}
			inputData.close();
		}
		catch(IOException exc){batchDisplay.appendText("Ошибка при закрытии файла log.txt");}
	}
}