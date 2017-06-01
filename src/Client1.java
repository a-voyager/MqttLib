import callback.MessageCallBack;

import java.util.Scanner;

/**
 * Created by wuhaojie on 17-5-25.
 */
public class Client1 {

    public static void main(String[] args) {
        Connector<Message> connector = new Connector.Builder<Message>()
                .setServerURI("tcp://0.0.0.0:61613")
                .setClientId("#1")
                .setClientTopic("client")
                .setMessageClassType(Message.class)
                .build();


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
            connector.sendMessage("server", new Message(1, s));
        }
    }

}
