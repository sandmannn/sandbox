package edu.coursera.distributed;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * A basic and very limited implementation of a file server that responds to GET
 * requests from HTTP clients.
 */
public final class FileServer {
    /**
     * Main entrypoint for the basic file server.
     *
     * @param socket Provided socket to accept connections on.
     * @param fs A proxy filesystem to serve files from. See the PCDPFilesystem
     *           class for more detailed documentation of its usage.
     * @param ncores The number of cores that are available to your
     *               multi-threaded file server. Using this argument is entirely
     *               optional. You are free to use this information to change
     *               how you create your threads, or ignore it.
     * @throws IOException If an I/O error is detected on the server. This
     *                     should be a fatal error, your file server
     *                     implementation is not expected to ever throw
     *                     IOExceptions during normal operation.
     */
    public void run(final ServerSocket socket, final PCDPFilesystem fs,
            final int ncores) throws IOException {

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(ncores);

        while (true) {
            Socket s = socket.accept();

            executor.execute(
//            Thread t = new Thread(){
//            {
//                @Override
//                public void run
            () ->{
                    try {
                        InputStream is = s.getInputStream();
                        BufferedReader bf = new BufferedReader(new InputStreamReader(is));
                        String line = bf.readLine();
                        String[] spl = line.split(" ");
                        if (!spl[0].equals("GET")) {
                            System.out.println("bad");
                            return;
                        }
                        PCDPPath path = new PCDPPath(spl[1]);
                        String fl = fs.readFile(path);

                        PrintWriter bw = new PrintWriter(s.getOutputStream());
                        if (fl != null) {
                            bw.write("HTTP/1.0 200 OK\r\n");
                            bw.write("Server: FileServer\r\n");
                            bw.write("\r\n");
                            bw.write(fl);
                        } else {
                            bw.write("HTTP/1.0 404 Not Found\r\n");
                            bw.write("Server: FileServer\r\n");
                            bw.write("\r\n");
                        }
                        bw.close();

                    } catch (IOException e) {
                        System.out.println("IO Exception");
                    }
                }
//            }
            );
//            t.start();


        }
    }
}
