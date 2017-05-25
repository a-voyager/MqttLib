import callback.MessageCallBack;

import java.util.Scanner;

/**
 * Created by wuhaojie on 17-5-25.
 */
public class Client2 {

    public static void main(String[] args) {
        Connector<Message> connector = Connector.defaultConnector(Message.class, "#2", "client");
        connector.init();
        connector.receiveMessage(new MessageCallBack<Message>() {
            @Override
            public void onNewMessage(Message message) {
                System.out.println(message);
            }
        });
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String s = scanner.next();
            connector.sendMessage("server", new Message(2, s));
        }
    }

}