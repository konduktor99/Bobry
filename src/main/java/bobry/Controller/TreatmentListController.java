package bobry.Controller;

import bobry.InjuryCategory;
import bobry.Physiotherapist;
import bobry.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TreatmentListController implements Initializable {

    @FXML
    private ImageView assignButtonImg;

    @FXML
    private Button assignPhysioButton;

    @FXML
    private TableColumn<Player.Treatment, String> descCol;

    @FXML
    private TableColumn<Player.Treatment, Integer> idCol;

    @FXML
    private TableColumn<Player.Treatment, InjuryCategory> injuryCatCol;

    @FXML
    private TableColumn<Player.Treatment, Physiotherapist> physioCol;

    @FXML
    private TableColumn<Player.Treatment, Player> playerCol;

    @FXML
    private TableColumn<Player.Treatment, LocalDate> sinceCol;

    @FXML
    private TableColumn<Player.Treatment, LocalDate> tillCol;

    @FXML
    private TableView<Player.Treatment> treatmentTable;

    @FXML
    private Text treatmentsHeader;

    @FXML
    private Text validationAlert;

    private static Player.Treatment selectedTreatment;

    public static Player.Treatment getSelectedTreatment() {
        return selectedTreatment;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        injuryCatCol.setCellValueFactory(new PropertyValueFactory<>("injuryCategory"));
        physioCol.setCellValueFactory(new PropertyValueFactory<>("physiotherapist"));
        playerCol.setCellValueFactory(new PropertyValueFactory<>("player"));
        sinceCol.setCellValueFactory(new PropertyValueFactory<>("since"));
        tillCol.setCellValueFactory(new PropertyValueFactory<>("till"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        treatmentTable.setItems(treatmentsObservableList);

    }


    @FXML
    private void textDisappear(){
        validationAlert.setVisible(false);
    }


    @FXML
    private void handleAssignButton(ActionEvent event) throws IOException {


        selectedTreatment = treatmentTable.getSelectionModel().getSelectedItem();
        if (selectedTreatment != null) {
            if (selectedTreatment.getPhysiotherapist() != null) {
                validationAlert.setVisible(true);
                validationAlert.setText("Physiotherapist already assigned.");
            }
            if (selectedTreatment.getTill() != null) {
                validationAlert.setVisible(true);
                validationAlert.setText("Treatment is finished.");
            }

            if(selectedTreatment.getTill() == null && selectedTreatment.getPhysiotherapist() == null){

                Parent root = FXMLLoader.load(getClass().getResource("/PhysiotherapistListView.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                
            }





        }
    }


    private ObservableList<Player.Treatment> treatmentsObservableList = FXCollections.observableArrayList(
            Player.getTreatmentsList()
    );
}

