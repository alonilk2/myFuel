package gui;

import common.ClientIF;
import control.ClientController;
import control.ReplyReportSaleSelectionController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ReplyReportSaleSelectionForm extends Application implements ClientIF{
	private ClientController client;
	private ReplyReportSaleSelectionController rrssController;
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.rrssController = new ReplyReportSaleSelectionController(this.client);
		try {
			FXMLLoader fxmload = new FXMLLoader();
			fxmload.setLocation(getClass().getResource("ReplyReportSaleSelection.fxml"));
			fxmload.setController(rrssController);
			AnchorPane root = (AnchorPane)fxmload.load();
			Scene scene = new Scene(root,772,550);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public ReplyReportSaleSelectionForm(ClientController client, Stage stage) throws Exception {
		this.client = client;
	}


	@Override
	public boolean sendToController(Object obj) {
		rrssController.getObjectFromUI(obj);
		return false;
	}

}
