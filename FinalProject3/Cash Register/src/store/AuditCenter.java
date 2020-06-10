package store;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

public class AuditCenter extends Thread {

    private Thread t;
    private String threadName;

    public AuditCenter(String ...t) {
        this.threadName = t[0];
        System.out.println("Creating " + threadName);
    }

    public void run() {
        System.out.println("Running " + threadName);
        try{
//            for(int i = 0; i < 5; i++) {
                System.out.println("Thread " + threadName);
                Thread.sleep(10000);
//            }
        } catch (InterruptedException e) {
            System.out.println("Thread " + threadName + " interrupted.");
        }
        System.out.println("Thread " + threadName + " exiting");

    }

    public void start() {
        System.out.println("Starting " + threadName);
        if(t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }

    public static void audit(String action, AuditCenter ...t) throws IOException {
        Date date = new Date();
        long time = date.getTime();
        Timestamp ts = new Timestamp(time);
        FileWriter fileWriter = new FileWriter("C:\\Users\\m_mel\\Desktop\\AuditFile.csv", true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.append(t[0].threadName + "," + ts + "\n");
        bufferedWriter.close();
    }
}
