package pdfperera;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigPort{

    @Value("${custom.server.port}")
    private int serverPort = 20801;


    public void setServerPort(int port) {
        this.serverPort = port;
    }
}

// MyComponent myComponent = new MyComponent();
// myComponent.setServerPort(<una porta su cui ascoltare>);
