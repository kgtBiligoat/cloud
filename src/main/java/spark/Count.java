package spark;

import hbase.HBaseConf;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Count {
    private String getplaceId()
    {
        String placeId;
        try {
            System.out.println("请输入placeId：");
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            placeId=in.readLine();
        }
        catch (Exception e)
        {
            System.out.println("输入错误!");
            String a = getplaceId();
            return a;
        }
        return placeId;
    }

    public void Filtra() throws IOException {
        String i = getplaceId();
        String tablename = "VehicleCount";
        Connection connection = HBaseConf.getConnection();
        Admin admin = connection.getAdmin();
        TableName name = TableName.valueOf(tablename);
        List list = new ArrayList();
        if (admin.tableExists(name)) {
            try {
                TableName tableName = TableName.valueOf(Bytes.toBytes(tablename));
                Table table = connection.getTable(tableName);
                SparkMap map = new SparkMap();

                Scan scan = new Scan();
                Filter filter = new RowFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(i)));
                scan.setFilter(filter);

                ResultScanner scanner = table.getScanner(scan);
                for (Result result : scanner) {
                    list.add(map.resultMapToRecord(result).getPlaceId());
                    list.add(map.resultMapToRecord(result).getCount());
                    System.out.println(list);
                    list.clear();
                }

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("Table doesn't exist!");
        }
    }

    public static void main(String[] args) {
        try {
            Count meet = new Count();
            while (true){
                meet.Filtra();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
