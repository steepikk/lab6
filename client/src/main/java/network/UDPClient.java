package network;

import com.google.common.primitives.Bytes;
import network.requests.Request;
import network.responses.Response;
import org.apache.commons.lang3.SerializationUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeMap;

public class UDPClient {
    private final int PACKET_SIZE = 20;
    private final int DATA_SIZE = PACKET_SIZE - 2;

    private final DatagramChannel client;
    private final InetSocketAddress addr;

    public UDPClient(InetAddress address, int port) throws IOException {
        this.addr = new InetSocketAddress(address, port);
        this.client = DatagramChannel.open().bind(null).connect(addr);
        this.client.configureBlocking(false);
    }

    public Response sendAndReceiveCommand(Request request) throws IOException {
        var data = SerializationUtils.serialize(request);
        var responseBytes = sendAndReceiveData(data);

        Response response = SerializationUtils.deserialize(responseBytes);
        return response;
    }

    private void sendData(byte[] data) throws IOException {
        int num_packets = (int) Math.ceil(data.length / (double)DATA_SIZE);
        byte[][] ret = new byte[(int)Math.ceil(data.length / (double)DATA_SIZE)][DATA_SIZE];

        int start = 0;
        for(int i = 0; i < ret.length; i++) {
            ret[i] = Arrays.copyOfRange(data, start, start + DATA_SIZE);
            start += DATA_SIZE;
        }

        for(int i = 0; i < ret.length; i++) {
            var chunk = ret[i];
            var new_chunk = Bytes.concat(chunk, new byte[]{(byte) num_packets, (byte) (i + 1)});
            client.send(ByteBuffer.wrap(new_chunk), addr);
        }
    }

    private byte[] receiveData() throws IOException {
        int count = 0;
        var receivedPackets = new HashMap<Integer, byte[]>();
        var received = false;
        var result = new byte[0];

        while(!received) {
            var data = receiveData(PACKET_SIZE);
            count += 1;

            int num_packets = data[data.length - 2] & 0xFF;
            int num_packet = data[data.length - 1];

            if (num_packets < 0) {
                num_packets += 256;
            }

            receivedPackets.put(num_packet, Arrays.copyOf(data, data.length - 2));

            if (count == num_packets) {
                received = true;
            }
        }

        TreeMap<Integer, byte[]> sortedReceivedPackets = new TreeMap<>(receivedPackets);

        for (byte[] packetData : sortedReceivedPackets.values()) {
            result = Bytes.concat(result, packetData);
        }

        return result;
    }

    private byte[] receiveData(int bufferSize) throws IOException {
        var buffer = ByteBuffer.allocate(bufferSize);
        SocketAddress address = null;
        while(address == null) {
            address = client.receive(buffer);
        }
        return buffer.array();
    }

    private byte[] sendAndReceiveData(byte[] data) throws IOException {
        sendData(data);
        return receiveData();
    }
}
