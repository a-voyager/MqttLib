import callback.MessageCallBack;

import java.util.Scanner;

/**
 * Created by wuhaojie on 17-5-25.
 */
public class Client1 {

    public static void main(String[] args) {
        Connector<Message> connector = Connector.defaultConnector(Message.class, "#1", "client");
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
        // todo 1.客户端发送失败 需要放入队列等待
        // todo 2.服务器关闭后应该收到消息池中的消息
    }

}
