package program.accountant;

import database.groupsTable.GroupsEntity;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class GroupInfoWindowController implements Initializable {

    @FXML
    TextField numberCat, numberSubCat, numberGroup, descCat, descSubCat, descGroup, groupPercent;

    @FXML
    Button closeButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void initData(GroupsEntity groupsEntity){

        numberGroup.setText(groupsEntity.getGroupName());
        descGroup.setText(groupsEntity.getGroupDescription());
        groupPercent.setText(groupsEntity.getRateGroup().toString());

        numberSubCat.setText(groupsEntity.getSubCatId().getSubCatName());
        descSubCat.setText(groupsEntity.getSubCatId().getSubCatDescription());

        numberCat.setText(groupsEntity.getSubCatId().getCategoriesEntity().getCatName());
        descCat.setText(groupsEntity.getSubCatId().getCategoriesEntity().getCatDescription());

    }

    @FXML
    private void setCloseButton(){

        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
