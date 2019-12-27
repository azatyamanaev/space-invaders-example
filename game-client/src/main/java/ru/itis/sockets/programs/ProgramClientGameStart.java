package ru.itis.sockets.programs;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import ru.itis.sockets.clients.SocketClient;

@Parameters(separators = "=")
public class ProgramClientGameStart {
    @Parameter(names = "--server-ip")
    private String serverIp = "127.0.0.1";
    @Parameter(names = "--server-port")
    private Integer serverPort = 4321;

    public String getServerIp() {
        return serverIp;
    }

    public Integer getServerPort() {
        return serverPort;
    }

    public static void main(String... argv) {
        ProgramClientGameStart chatStart = new ProgramClientGameStart();
        JCommander.newBuilder()
                .addObject(chatStart)
                .build()
                .parse(argv);
        chatStart.run();
    }

    public void run() {
        SocketClient client = new SocketClient();
        client.startConnection(getServerIp(), getServerPort());
        ;
    }
}
