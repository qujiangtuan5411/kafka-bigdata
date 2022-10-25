package com.dada.dm.qujia.model;

import lombok.Data;

/**
 * @author jt.Qu
 * @description ip port
 * @program: dw-metadata
 * @date 2022-05-14 18:07
 */
@Data
public class IpPort {

    String ips;
    String port;
    String namespace;

    public IpPort() {
    }

    public IpPort(String ips, String port, String namespace) {
        this.ips = ips;
        this.port = port;
        this.namespace = namespace;
    }
}
