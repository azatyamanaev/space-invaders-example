package ru.itis.programs;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import ru.itis.sockets.servers.GameServer;

@Parameters(separators = "=")
public class ProgramGameServer {
    @Parameter(names = "--server-port")
    private Integer serverPort = 4321;
    @Parameter(names = "--db-properties")
    private String dbProperties = "/home/monitor/Рабочий стол/Project(game)/Space Invaders/src/main/db.properties";

    public Integer getServerPort() {
        return serverPort;
    }

    public String getDbProperties() {
        return dbProperties;
    }

    public static void main(String... argv) {
        ProgramGameServer gameStart = new ProgramGameServer();
        JCommander.newBuilder()
                .addObject(gameStart)
                .build()
                .parse(argv);
        gameStart.run();
    }

    public void run() {
        GameServer server = new GameServer();
        server.start(getServerPort());
    }
}
