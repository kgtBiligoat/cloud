package HBaseSearch;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hbase.*;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
public class Inquire {


    public void rowfilter(String tablename, String rowkeyvalue) throws IOException {
        Connection connection = HBaseConf.getConnection();
        Admin admin = connection.getAdmin();
        TableName name = TableName.valueOf(tablename);
        List list = new ArrayList();
        if (admin.tableExists(name)){
            try {
                TableName tableName = TableName.valueOf(Bytes.toBytes(tablename));
                Table table = connection.getTable(tableName);
                Get get = new Get(Bytes.toBytes(rowkeyvalue));
                Map recordMap =new Map();


                Filter filter = new QualifierFilter(CompareOperator.EQUAL, new BinaryComparator(Bytes.toBytes("address")));
                Filter filter1 = new QualifierFilter(CompareOperator.EQUAL, new BinaryComparator(Bytes.toBytes("latitude")));
                Filter filter2 = new QualifierFilter(CompareOperator.EQUAL, new BinaryComparator(Bytes.toBytes("longitude")));
                Filter filter3 = new QualifierFilter(CompareOperator.EQUAL, new BinaryComparator(Bytes.toBytes("placeId")));
                Filter filter4 = new QualifierFilter(CompareOperator.GREATER_OR_EQUAL, new BinaryComparator(Bytes.toBytes("Time")));


                Result result = table.get(get.setFilter(filter));
                Result result1 = table.get(get.setFilter(filter1));
                Result result2 = table.get(get.setFilter(filter2));
                Result result3 = table.get(get.setFilter(filter3));
                Result result4 = table.get(get.setFilter(filter4));

                if (recordMap.resultMapToRecord(result4).getPlaceId()!=0) {
                    list.add(recordMap.resultMapToRecord(result).getAddress()+" "
                            + recordMap.resultMapToRecord(result1).getLatitude()+" "
                            + recordMap.resultMapToRecord(result2).getLongitude());
                            //+ recordMap.resultMapToRecord(result3).getPlaceId()+" "
                            //+ recordMap.resultMapToRecord(result4).getTime());
                    System.out.println(list);
                }else {
                    System.out.println("does not exist");
                }
                //list.clear();

                /*System.out.println(recordMap.resultMapToRecord(result).getAddress());
                System.out.println(recordMap.resultMapToRecord(result1).getLatitude());
                System.out.println(recordMap.resultMapToRecord(result2).getLongitude());
                System.out.println(recordMap.resultMapToRecord(result4).getTime());*/


                /*if (recordMap.resultMapToRecord(result3).getPlaceId()!=0) {
                    System.out.println(recordMap.resultMapToRecord(result).getAddress());
                    System.out.println(recordMap.resultMapToRecord(result1).getLatitude());
                    System.out.println(recordMap.resultMapToRecord(result2).getLongitude());
                    System.out.println(recordMap.resultMapToRecord(result3).getPlaceId());
                    System.out.println(recordMap.resultMapToRecord(result4).getTime());
                    //System.out.println(recordMap.resultMapToRecord(rs).getPlaceId());
                }else System.out.println("Record doe not exist. ");
                */
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        else System.out.println("table not exits");
        //connection.close();
        //admin.close();
    }
    public static void main(String[] args) throws IOException {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            Inquire inquire =new Inquire();
            while (true) {
                String time = in.readLine();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = simpleDateFormat.parse(time);
                long ts = date.getTime();
                time = String.valueOf(ts);
                Long l;
                l = Long.parseLong(time)/1000;
                String rkvalue = l+"##"+in.readLine();
                System.out.println(rkvalue);
                String tablename = "search";
                inquire.rowfilter(tablename, rkvalue);
            }
        }catch (IOException e){
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}