import java.util.Scanner;

/**
 * Created by wuhaojie on 17-5-25.
 */
public class Client {

    public static void main(String[] args) {
        Connector<Message> connector = Connector.defaultConnector(Message.class, "client");
        connector.init();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String s = scanner.next();
            connector.sendMessage(new Message(1, s));
        }
    }

}
