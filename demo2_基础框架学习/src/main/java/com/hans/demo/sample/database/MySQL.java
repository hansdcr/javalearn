package com.hans.demo.sample.database;

import com.hans.demo.sample.IConnect;

public class MySQL implements IConnect {
    private String ip = "localhost";
    private Integer port = 3306;

    public MySQL(String ip, Integer port) {
        this.ip = ip;
        this.port = port;
    }


    public void connect(String ip, Integer port) {
        this.ip = ip;
        this.port = port;
    }

    public void connect() {
        System.out.println(this.ip +":" + this.port);
    }
}
