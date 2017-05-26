/**
 * Created by wuhaojie on 17-5-26.
 */
public class LooperTest {

    public static void main(String[] args) {

        Looper.prepare(new Looper.CallBack() {
            @Override
            public void onNewPoll(MessageQueue.QMessage message) {
                System.out.println(message);
                MessageQueue.put(message, 2000);
            }
        });

        MessageQueue.put(new MessageQueue.QMessage("topic", "message"));

        Looper.loop();


    }

}
