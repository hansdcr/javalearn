package com.hans.demo.sample;

import com.hans.demo.sample.database.MySQL;

public class DatabaseConfiguration {
    private String ip;
    private Integer port;

    public IConnect mysql() {
        return new MySQL(this.ip, this.port);
    }
}
