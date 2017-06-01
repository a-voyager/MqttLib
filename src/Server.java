import callback.MessageCallBack;

/**
 * Created by wuhaojie on 17-5-25.
 */
public class Server {

    public static void main(String[] args) {
        Connector<Message> connector = Connector.defaultConnector(Message.class, "server");


        connector.init();
        connector.receiveMessage(new MessageCallBack<Message>() {
            @Override
            public void onNewMessage(Message message) {
                System.out.println(message);
                connector.sendMessage("client", new Message(0, "收到"));
            }
        });


    }
}
