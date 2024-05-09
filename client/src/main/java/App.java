import cli.Runner;
import input.Console;
import network.UDPClient;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Главный класс клиентского приложения.
 *
 * @author steepikk
 */
public class App {
    private static final int PORT = 1821;

    public static void main(String[] args) {
        var console = new Console();
        try {
            var client = new UDPClient(InetAddress.getLocalHost(), PORT);
            var cli = new Runner(client, console);

            cli.interactiveMode();
        } catch (IOException e) {
            System.out.println("Невозможно подключиться к серверу!");
        }
    }
}
