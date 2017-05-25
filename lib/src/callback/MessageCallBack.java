package callback;

/**
 * Created by wuhaojie on 17-5-25.
 */
public interface MessageCallBack<T> {

    void onNewMessage(T message);

}
