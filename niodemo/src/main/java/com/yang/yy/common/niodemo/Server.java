package com.yang.yy.common.niodemo;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Data
public class Server {

    private Integer port;


    private volatile Integer workCount;
    private List<Runnable> works;
    private final BlockingQueue<SocketChannel> queue = new LinkedBlockingQueue<>();

    private Lock lock = new ReentrantLock();

    public Server(Integer port, Integer workCapacity) {
        this.port = port;
        this.works = Lists.newArrayListWithCapacity(workCapacity);
        for (int i = 0; i < workCapacity; i++) {
            Thread worker = new Thread(new Worker());
            worker.start();
            works.add(worker);
        }
    }

    public void start() {
        if (port <= 0) {
            throw new RuntimeException("端口不合法");
        }
        ServerSocketChannel serverSocketChannel = null;
        try {
            serverSocketChannel = ServerSocketChannel.open();
            //设置是否阻塞
            serverSocketChannel.configureBlocking(false);

            ServerSocket serverSocket = serverSocketChannel.socket();
            serverSocket.bind(new InetSocketAddress(port));

            Selector selector = Selector.open();

            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                int n = selector.select(10000);
                if (n == 0) continue;

                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                        SocketChannel channel = server.accept();
                        registerChannel(selector, channel, SelectionKey.OP_READ);
                        sayHello(channel);
                    }
                    if (selectionKey.isReadable()) {

                        queue.put((SocketChannel) selectionKey.channel());
                    }
                    iterator.remove();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("启动失败", e);
        } finally {
            if (serverSocketChannel != null) {
                try {
                    serverSocketChannel.close();
                } catch (IOException e) {
                    log.error("关闭异常", e);
                }
            }
        }

    }


    private void registerChannel(Selector selector, SocketChannel channel, int opRead) throws IOException {
        if (channel == null) return;
        channel.configureBlocking(false);
        channel.register(selector, opRead);
    }

    private ByteBuffer serverBuffer = ByteBuffer.allocateDirect(2048);

    private void sayHello(SocketChannel channel) throws IOException {
        serverBuffer.clear();
        serverBuffer.put("hello nio".getBytes());
        serverBuffer.flip();
        channel.write(serverBuffer);
    }


    public class Worker implements Runnable {
        private ByteBuffer buffer = ByteBuffer.allocateDirect(2048);

        @Override
        public void run() {
            log.info("{} running", Thread.currentThread().getName());
            while (true) {
                try {

                    SocketChannel channel = queue.take();
                    log.info("server by {}", Thread.currentThread().getName());
                    readFromChannel(channel);
                } catch (InterruptedException e) {
                    Thread.interrupted();
                } catch (Exception e) {
                    log.error("Worker inner exception ", e);
                }
            }
        }


        private void readFromChannel(SocketChannel channel) {
            if (channel == null) {
                return;
            }
            try {
                buffer.clear();
                int len;
                while ((len = channel.read(buffer)) > 0) {
                    buffer.flip();
                    if (buffer.hasRemaining()) {
                        channel.write(buffer);
                    }
                    buffer.clear();
                }
                //连接关闭的情况
                if (len < 0) {
                    log.info("{} 连接关闭", channel.getLocalAddress());
                    channel.close();
                }
            } catch (Exception e) {
                log.error("读取数据时异常", e);
            }
        }

    }
}
