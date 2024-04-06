import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Handler;
import org.json.JSONObject;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NetworkCellService extends Service {
    private Handler handler = new Handler();
    private final int PORT = 12345;
    private final String SERVER_IP = "192.168.1.100"; // Change to your server IP
    private Socket socket;
    private PrintWriter out;

    private Runnable sendDataRunnable = new Runnable() {
        @Override
        public void run() {
            collectAndSendData();
            handler.postDelayed(this, 10000); // Schedule every 10 seconds
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler.post(sendDataRunnable);
        return START_STICKY;
    }

    private void collectAndSendData() {
        // Collect cell info using TelephonyManager or other APIs
        try {
            JSONObject data = new JSONObject();
            data.put("operator", "Example Operator");
            data.put("signal_power", -50);
            data.put("sinr", 10.0);
            data.put("network_type", "4G");
            data.put("frequency_band", "20");
            data.put("cell_id", "12345-67890");
            data.put("timestamp", new SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.ENGLISH).format(new Date()));

            sendDataToServer(data.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendDataToServer(String data) {
        try {
            socket = new Socket(SERVER_IP, PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println(data);
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacks(sendDataRunnable);
        try {
            if (socket != null) socket.close();
            if (out != null) out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
