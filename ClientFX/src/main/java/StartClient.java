import controller.LoginC;
import controller.StartPageC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import services.IService;

public class StartClient  extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-client.xml");
        IService serv = (IService) factory.getBean("serv");
        System.out.println("Obtained a reference to remote chat server");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/loginView.fxml"));
        Parent root = loader.load();
        //
        LoginC ctrl = loader.getController();
        ctrl.setService(serv);

        FXMLLoader cloader = new FXMLLoader(
                getClass().getResource("/StartPageView.fxml"));
        Parent croot = cloader.load();

        StartPageC concursCtrl = cloader.getController();
        concursCtrl.setService(serv);

        ctrl.setControllerPrincipal(concursCtrl);
        ctrl.setParent(croot);

        primaryStage.setTitle("LogIn");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
