package com.jengine.j2se.concurrent.threadCommunication.waitNotifyPractice;

import org.junit.Test;

import java.io.*;

/**
 * @author nouuid
 * @date 4/5/2016
 * @description
 */
public class PipeInputOutputRunner {

    @Test
    public void PipeInputOutputRunnerByteStreamTest() throws Exception {
        pipeByteStreamTest();
        Thread.sleep(10*1000);
    }

    @Test
    public void PipeInputOutputRunnerCharacterStreamTest() throws Exception {
        pipeCharacterStreamTest();
        Thread.sleep(10*1000);
    }

    public void pipeByteStreamTest() throws IOException, InterruptedException {
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream();
        pipedInputStream.connect(pipedOutputStream);

        PipeInputOutputByteStreamWriter pipeInputOutputByteStreamWriter = new PipeInputOutputByteStreamWriter(pipedOutputStream);
        PipeInputOutputByteStreamReader pipeInputOutputByteStreamReader = new PipeInputOutputByteStreamReader(pipedInputStream);

        Thread readData = new Thread(new Runnable() {
            @Override
            public void run() {
                pipeInputOutputByteStreamWriter.write();
            }
        });

        Thread writeData = new Thread(new Runnable() {
            @Override
            public void run() {
                pipeInputOutputByteStreamReader.read();
            }
        });

        writeData.start();
        Thread.sleep(2 * 1000);
        readData.start();
    }

    public void pipeCharacterStreamTest() throws IOException, InterruptedException {
        PipedWriter pipedWriter = new PipedWriter();
        PipedReader pipedReader = new PipedReader();
        pipedReader.connect(pipedWriter);

        PipeInputOutputCharacterStreamReader pipeInputOutputCharacterStreamReader = new PipeInputOutputCharacterStreamReader(pipedReader);
        PipeInputOutputCharacterStreamWriter pipeInputOutputCharacterStreamWriter = new PipeInputOutputCharacterStreamWriter(pipedWriter);

        Thread dataReader = new Thread(new Runnable() {
            @Override
            public void run() {
                pipeInputOutputCharacterStreamReader.read();
            }
        });

        Thread dataWriter = new Thread(new Runnable() {
            @Override
            public void run() {
                pipeInputOutputCharacterStreamWriter.write();
            }
        });

        dataReader.start();
        Thread.sleep(2*1000);
        dataWriter.start();

    }
}

class PipeInputOutputByteStreamWriter {
    private PipedOutputStream pipedOutputStream;

    public PipeInputOutputByteStreamWriter(PipedOutputStream pipedOutputStream) {
        this.pipedOutputStream = pipedOutputStream;
    }

    public void write() {
        System.out.println("write:");
        String numStr = "";
        try {
            for (int i = 0; i < 200; i++) {
                numStr = "";
                numStr += i + 1 + " ";
                pipedOutputStream.write(numStr.getBytes());
                System.out.print(numStr);
            }
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                pipedOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class PipeInputOutputByteStreamReader {

    private PipedInputStream pipedInputStream;

    public PipeInputOutputByteStreamReader(PipedInputStream pipedInputStream) {
        this.pipedInputStream = pipedInputStream;
    }

    public void read() {
        System.out.println("read:");
        try {
            byte[] byteArray = new byte[20];
            int readLength = pipedInputStream.read(byteArray);
            while (readLength != -1) {
                String newData = new String(byteArray, 0, readLength);
                System.out.print(newData);
                readLength = pipedInputStream.read(byteArray);
            }
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                pipedInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class PipeInputOutputCharacterStreamWriter {

    private PipedWriter pipedWriter;

    public PipeInputOutputCharacterStreamWriter(PipedWriter pipedWriter) {
        this.pipedWriter = pipedWriter;
    }

    public void write() {
        System.out.println("write:");
        String numStr = "";
        try {
            for (int i = 0; i < 200; i++) {
                numStr = "";
                numStr += i + 1 + " ";
                pipedWriter.write(numStr);
                System.out.print(numStr);
                Thread.sleep(1000);
            }
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pipedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

class PipeInputOutputCharacterStreamReader {

    private PipedReader pipedReader;

    public PipeInputOutputCharacterStreamReader(PipedReader pipedReader) {
        this.pipedReader = pipedReader;
    }

    public void read() {
        try {
            System.out.println("read:");
            char[] chars = new char[20];
            int readLength = pipedReader.read(chars);
            while (readLength != -1) {
                String newData = new String(chars, 0, readLength);
                System.out.print(newData);
                Thread.sleep(1000);
                readLength = pipedReader.read(chars);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

