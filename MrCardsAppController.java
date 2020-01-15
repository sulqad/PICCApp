package mrcards;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import mrcards.SystemMethods.*;
import mrcards.ReadData;
import mrcards.UpdateData;
import mrcards.LoadKey;

import javax.smartcardio.*;

public class MrCardsAppController {

	@FXML private TextArea display;
	@FXML private TextField defineKeyType;
	@FXML private TextField loadKey;
	@FXML private TextField blockAddress;
	@FXML private TextField setData;
	
	@FXML private TextArea batchDisplay;
	@FXML private Label batchLabel;
	@FXML private TextField batchBlockAddress;
	@FXML private TextField batchTrailerAddress;
	@FXML private TextField trailerData;
	
	@FXML protected void batchProcessing(ActionEvent event){
		
		Integer blockAddressObj =Integer.valueOf(batchBlockAddress.getText());
		int bAddress =blockAddressObj;
		bAddress -= 1;
		
		Integer trailerAddressObj =Integer.valueOf(batchTrailerAddress.getText());
		int tAddress =trailerAddressObj;
		tAddress -= 1;
		
		String kType =defineKeyType.getText();
		String tData =trailerData.getText();

		Batch updateBlock =new Batch(bAddress, tAddress, kType, tData, batchDisplay);
	}
	
	@FXML protected void stopBatchProcessing(ActionEvent event){
		
	}
	
	@FXML protected void clearDisplay(ActionEvent event){
		
		display.clear();
	}
	
	@FXML protected void clearBatchDisplay(ActionEvent event){
		
		batchDisplay.clear();
	}
	
	@FXML protected void connectTerminal(ActionEvent event){
		
		if(SystemMethods.connectTerminal()){
			display.appendText("Терминал подключен\n");
		}
		else display.appendText("Терминал не обнаружен.\n");
	}
	
	@FXML protected void loadKey(ActionEvent event){
		
		String key = loadKey.getText();
		LoadKey loadKey =new LoadKey(key, display);
		
	}
	
	@FXML protected void readBlock(ActionEvent event){
		
		Integer blockAddressObj =Integer.valueOf(blockAddress.getText());
		int bAddress =blockAddressObj;
		bAddress -= 1;
		String keyType = defineKeyType.getText();
		String key = loadKey.getText();
		ReadData readBlock =new ReadData(key, bAddress, keyType, display);
		
	}
	
	@FXML protected void updateBlock(ActionEvent event){
		
		Integer blockAddressObj =Integer.valueOf(blockAddress.getText());
		int bAddress =blockAddressObj;
		bAddress -= 1;
		String keyType = defineKeyType.getText();
		String key = loadKey.getText();
		UpdateData updateBlock =new UpdateData(key, bAddress, keyType, display, setData);
	}
}

