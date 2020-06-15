package gui;

import common.ClientIF;
import control.ClientController;
import control.SetUpdatePriceController;
import control.saleTemplateController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class saleTemplateForm extends Application implements ClientIF{
	private Stage mainStage;
	private ClientController client;
	//////////////////////////////////////
	private saleTemplateController sale;
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.sale = new saleTemplateController(this.client);
		try {
			FXMLLoader fxmload = new FXMLLoader();
			fxmload.setLocation(getClass().getResource("saleTemplate.fxml"));
			fxmload.setController(sale);
			AnchorPane root = (AnchorPane)fxmload.load();
			Scene scene = new Scene(root,772,550);
			scene.getStylesheets().add(getClass().getResource("prototype.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public saleTemplateForm(ClientController client, Stage stage) throws Exception {
		this.client = client;
	}
	


	@Override
	public boolean sendToController(Object obj) {
		sale.getObjectFromUI(obj);
		return false;
	}
}
