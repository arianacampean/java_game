package controller;

import domeniu.Jucator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ObiecteException;
import services.IService;

public class LoginC {
    StartPageC crtPrincipal;
    Parent prt;
    @FXML
    TextField username;
    @FXML
    TextField parola;


    @FXML
    private void logIn(ActionEvent actionEvent) throws ObiecteException {
        String prim=username.getText();
        String doi=parola.getText();
        Jucator arb=new Jucator(prim,doi);
        try {

            serv.login(arb,crtPrincipal);
            Jucator arbb=serv.get_oneJuc(arb.getUsername(),arb.getParola());
            Stage stage=new Stage();
            stage.setTitle("Start Page");
            stage.setScene(new Scene(prt));
            crtPrincipal.setJuc(arbb);
            stage.show();
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
//
    }




    private IService serv;
    public void setService(IService serv) {
        this.serv = serv;
    }
    public void setControllerPrincipal(StartPageC crt){
        crtPrincipal=crt;
    }
    public void setParent(Parent p) {
        this.prt = p;
    }
}
