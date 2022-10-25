package com.dada.dm.qujia.util;


/**
 * for: id生成策略
 *
 * @author hl.Wu
 * @date 2020/9/14 14:19
 * sioovm-client com.sioo.sioovmclient.common.utils
 * @since 0.0.1
 */
public class IdUtils {

    public static final Generator GENERATOR = new Generator(1);

    public static void main(String[] args) {
        Long id = IdUtils.GENERATOR.nextId();
        System.out.println("--id->"+id);
    }
}
