package HBaseSearch;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import spark.DateTransform;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ErrorData {
    public static void main(String[] args) throws IOException {
        Configuration configuration = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(configuration);
        //建立表的连接
        Table table = connection.getTable(TableName.valueOf("error"));
        SysMap sysmap = new SysMap();
        List list = new ArrayList();
        //创建一个空的Scan实例
        Scan scan = new Scan();
        //在行上获取遍历器
        ResultScanner scanner = table.getScanner(scan);
        //打印行的值
        DateTransform dateTransform = new DateTransform();
        for (Result res : scanner) {
            list.add(sysmap.resultMapToRecord(res).getEid());
            list.add(dateTransform.DateTransform(String.valueOf(sysmap.resultMapToRecord(res).getTime())));
            list.add(sysmap.resultMapToRecord(res).getAddress());
            list.add(sysmap.resultMapToRecord(res).getLatitude());
            list.add(sysmap.resultMapToRecord(res).getLongitude());
            System.out.println(list);
            list.clear();
        }
        //关闭释放资源
        scanner.close();
    }
}
