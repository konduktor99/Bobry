package bobry.Controller;



import bobry.Physiotherapist;
import bobry.Player;
import bobry.WrongInputException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class PhysioListController implements Initializable {

    @FXML
    private Button assignButton;

    @FXML
    private ImageView assignButtonImg;

    @FXML
    private ImageView assignButtonImg1;

    @FXML
    private TableColumn<Physiotherapist, LocalDate> birthDateColumn;

    @FXML
    private TableColumn<Physiotherapist, String> firstNameCol;

    @FXML
    private TableColumn<Physiotherapist, String> lastNameCol;

    @FXML
    private ListView<Player.Treatment> physioTreatmentsList;

    @FXML
    private Button returnButton;

    @FXML
    private TableColumn<Physiotherapist, ?> specializationsCol;

    @FXML
    private TableView<Physiotherapist> physioTable;

    @FXML
    private Text physioHeader;

    @FXML
    private Text treatmentsHeader1;

    @FXML
    private Text validationAlert;

    @FXML
    private Text successInfo;

    @FXML
    private Text textFollowingTreatment;

    @FXML
    private Text textFollowingTreatmentLabel;

    private Physiotherapist selectedPhysio;

    @FXML
    private void handleAssign{

        ifButton(ActionEvent event)  (TreatmentListController.getSelectedTreatment().getPhysiotherapist()==null) {
            try {
                selectedPhysio.assignToTreatment(TreatmentListController.getSelectedTreatment());

                textFollowingTreatment.setVisible(false);
                textFollowingTreatmentLabel.setVisible(false);
                successInfo.setText("The physiotherapist assigned succesfully to the traetment.");
                physioTreatmentsList.setItems(getSelectedPhysioTreatmentsObservableList());

            } catch (WrongInputException e) {
                validationAlert.setVisible(true);
                validationAlert.setText("Injury type doesn't match physiotherapist's specializations.");
            }
        }
    }

    @FXML
    private void handleReturnButton(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/TreatmentListView.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void handlePhysioChoice(MouseEvent event) {

        physioTreatmentsList.setItems(getSelectedPhysioTreatmentsObservableList());
        textDisappear();


    }

    private void textDisappear() {

        validationAlert.setVisible(false);
    }

    private ObservableList<Player.Treatment> getSelectedPhysioTreatmentsObservableList(){
        selectedPhysio = physioTable.getSelectionModel().getSelectedItem();
        ObservableList<Player.Treatment> physioTreatmentsObservableList = FXCollections.observableArrayList(
                selectedPhysio.getTreatmentsList()
        );
        return physioTreatmentsObservableList;
    }


    private ObservableList<Physiotherapist> getPhysiotherapistObservableList() {

        ObservableList<Physiotherapist> physiotherapistObservableList = FXCollections.observableArrayList(
                Physiotherapist.getPhysiotherapistsList()
        );
    return  physiotherapistObservableList;
    }


    public void initialize(URL url, ResourceBundle resourceBundle) {


        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        birthDateColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        specializationsCol.setCellValueFactory(new PropertyValueFactory<>("specializations"));


        physioTable.setItems(getPhysiotherapistObservableList());
        physioTable.getSelectionModel().selectFirst();
        physioTreatmentsList.setItems(getSelectedPhysioTreatmentsObservableList());

        textFollowingTreatment.setText(TreatmentListController.getSelectedTreatment().toString());

    }

}
