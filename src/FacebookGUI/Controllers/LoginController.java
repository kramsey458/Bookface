package FacebookGUI.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import FacebookGUI.DataModel.UserModel;
import FacebookGUI.MainApp;


public class LoginController implements Controller {
    @FXML
    private Label labelStatus;

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPassword;

    private MainApp mainApp;

    public void Login(ActionEvent event) throws Exception{
        UserModel testUserModel = getCurrentUser(txtUsername.getText());
        if (testUserModel != null && testUserModel.validatePassword(txtPassword.getText())) {
            labelStatus.setText("Login Success");
            Stage stage = (Stage) labelStatus.getScene().getWindow();
            stage.close();
            mainApp.setCurrentUser(testUserModel);
            segueNewFrame("View/MainForm.fxml");
        } else {
            labelStatus.setText("Failed");
        }
    }

    public void SegueCreateAccount(ActionEvent event) throws Exception{
        segueNewFrame("View/CreateAccountForm.fxml");
    }

    public void segueNewFrame(String form) throws Exception{
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(MainApp.class.getResource(form));
        Stage primaryStage = new Stage();
        if (mainApp.getCurrentUser() != null)
            primaryStage.setTitle(mainApp.getCurrentUser().getFullName());
        Scene scene = new Scene(loader.load());
        setDestinationControllerDataSource(loader.getController());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
    }

    private UserModel getCurrentUser(String username){
        return mainApp.getUserData().searchUser(username);
    }

    private void setDestinationControllerDataSource(Controller controller){
        controller.setMainApp(mainApp);
    }
}
