package gui;

import common.ClientIF;
import control.ClientController;
import control.ReviewReportController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ReviewReportForm extends Application implements ClientIF{
	private ClientController client;
	private ReviewReportController rnmmController;
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.rnmmController = new ReviewReportController(this.client);
		try {
			FXMLLoader fxmload = new FXMLLoader();
			fxmload.setLocation(getClass().getResource("ReplyReportMarketingManager.fxml"));
			fxmload.setController(rnmmController);
			AnchorPane root = (AnchorPane)fxmload.load();
			Scene scene = new Scene(root,772,550);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public ReviewReportForm(ClientController client, Stage stage) throws Exception {
		this.client = client;
	}


	@Override
	public boolean sendToController(Object obj) {
		rnmmController.getObjectFromUI(obj);
		return false;
	}

}
