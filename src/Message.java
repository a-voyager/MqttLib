/**
 * Created by wuhaojie on 17-5-25.
 */
public class Message {

    int id;
    String text;

    public Message(int id, String text) {
        this.id = id;
        this.text = text;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }
}
