import exception.MqttClientInitException;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import utils.Log;

/**
 * Main class
 * Created by wuhaojie on 17-5-25.
 */
public class Connector {

    private static volatile Connector DEFAULT_CONNECTOR;

    private String user;
    private String password;
    private int connectionTimeout;
    private int keepAliveInterval;
    private String serverURI;
    private String clientId;

    private MqttClient mMqttClient;
    private MqttConnectOptions mOptions;


    private Connector() {
    }

    public static Connector defaultConnector() {
        if (DEFAULT_CONNECTOR == null) {
            synchronized (Connector.class) {
                if (DEFAULT_CONNECTOR == null) {
                    DEFAULT_CONNECTOR = new Builder().build();
                }
            }
        }
        return DEFAULT_CONNECTOR;
    }

    // Couldn't put it in constructor
    public void init() {
        // Mqtt 配置
        mOptions = new MqttConnectOptions();
        mOptions.setCleanSession(true);  // 是否每次连接后清除 session
        mOptions.setUserName(user);
        mOptions.setPassword(password.toCharArray());
        mOptions.setConnectionTimeout(connectionTimeout);   // 设置超时 单位秒
        mOptions.setKeepAliveInterval(keepAliveInterval);   // 心跳时间 每隔 1.5*time 秒发送消息判断客户是否在线

        // 创建远程的客户端
        try {
            mMqttClient = new MqttClient(serverURI, clientId, new MemoryPersistence());
        } catch (MqttException e) {
            throw new MqttClientInitException();
        }
        mMqttClient.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable throwable) {
                reConnect();
            }

            @Override
            public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
        try {
            mMqttClient.connect(mOptions);
            Log.d("Connect to " + serverURI + " succeed");
        } catch (MqttException e) {
            Log.d("Connect failed. " + e.getMessage());
            reConnect();
        }
    }

    private void reConnect() {
        if (mMqttClient == null) throw new MqttClientInitException();
        try {
            mMqttClient.connect(mOptions);
            Log.d("Reconnect succeed.");
        } catch (MqttException e) {
            Log.d("Reconnect failed. " + e.getMessage() + ". retry later");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e1) {
                Log.e(e1);
            }
            reConnect();
        }
    }


    public static class Builder {

        private String user = "admin";
        private String password = "password";
        private int connectionTimeout = 10;
        private int keepAliveInterval = 10;
        private String serverURI = "tcp://0.0.0.0:61613";
        private String clientId = "clientId";

        public Builder() {
        }

        public Builder setUser(String user) {
            this.user = user;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setConnectionTimeout(int connectionTimeout) {
            this.connectionTimeout = connectionTimeout;
            return this;
        }

        public Builder setKeepAliveInterval(int keepAliveInterval) {
            this.keepAliveInterval = keepAliveInterval;
            return this;
        }

        public Builder setServerURI(String serverURI) {
            this.serverURI = serverURI;
            return this;
        }

        public Builder setClientId(String clientId) {
            this.clientId = clientId;
            return this;
        }


        private void apply(Connector connector) {
            connector.user = this.user;
            connector.password = this.password;
            connector.connectionTimeout = this.connectionTimeout;
            connector.keepAliveInterval = this.keepAliveInterval;
            connector.serverURI = this.serverURI;
            connector.clientId = this.clientId;
        }

        public Connector build() {
            Connector connector = new Connector();
            apply(connector);
            return connector;
        }

    }


}
