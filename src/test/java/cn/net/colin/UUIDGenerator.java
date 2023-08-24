package cn.net.colin;

/**
 * @author SXF
 * @version 1.0
 * @description: TODO
 * http://seata.io/zh-cn/blog/seata-analysis-UUID-generator.html
 * https://seata.io/zh-cn/blog/seata-snowflake-explain.html
 * @date 2023/8/24 13:01
 */
public class UUIDGenerator {

    private static volatile IdWorker idWorker;

    public static long generateUUID() {
        if (idWorker == null) {
            synchronized (UUIDGenerator.class) {
                if (idWorker == null) {
                    init(1l);
                }
            }
        }
        return idWorker.nextId();
    }


    public static void init(Long serverNode) {
        idWorker = new IdWorker(serverNode);
    }

    public static void main(String[] args) {
        System.out.println(UUIDGenerator.generateUUID());
    }
}
