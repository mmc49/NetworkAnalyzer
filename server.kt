import java.net.ServerSocket
import java.net.Socket

fun server() {
    val server = ServerSocket(9999)
    val client = server.accept()
    val output = PrintWriter(client.getOutputStream(), true)
    val input = BufferedReader(InputStreamReader(client.inputStream))

    output.println("${input.readLine()} back")
}