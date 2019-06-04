package HBaseSearch;

import hbase.HBaseConf;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.util.Bytes;
import spark.DateTransform;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Sys {

    public static String savefile = "/home/hadoop/Desktop/syst.txt";
    public static String path = "/home/hadoop/Desktop/syst.txt";


    public static long test() {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date= null;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            date = myFormatter.parse(in.readLine());
        } catch (ParseException e) {
            System.out.println("输入错误请重新输入");
            long a=test();
            return a;
        } catch (IOException e) {

        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis()/1000;
    }


    public static String getid()
    {
        String pid;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while(true){
                System.out.println("请输入placeId：");
                pid=in.readLine();
                int i=Integer.parseInt(pid);
                if(i>=0&&i<=500) break;
            }
        }
         catch (Exception e)
    {
        System.out.println("输入错误请重新输入");
        String a = getid();
        return a;
    }
        return pid;
    }

    public void rowfilter(String tablename) throws IOException {


        java.lang.System.out.println("请输入开始时间：");
        long start= test();
        System.out.println("请输入结束时间：");
        long end = test();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String pid = getid();

        Connection connection = HBaseConf.getConnection();
        Admin admin = connection.getAdmin();
        TableName name = TableName.valueOf(tablename);
        List list = new ArrayList();
        if (admin.tableExists(name)){
            try {
                TableName tableName = TableName.valueOf(Bytes.toBytes(tablename));
                Table table = connection.getTable(tableName);
                SysMap sysMap = new SysMap();

                Scan scan = new Scan();
                scan.setStartRow(Bytes.toBytes(pid +"##" + start ));
                scan.setStopRow(Bytes.toBytes(pid +"##" + end));
                Filter filter = new RowFilter(CompareFilter.CompareOp.EQUAL,new RegexStringComparator(pid+"##"+".*"));
                scan.setFilter(filter);

                DateTransform dateTransform = new DateTransform();
                ResultScanner scanner = table.getScanner(scan);
                for (Result r : scanner) {
                    list.add(dateTransform.DateTransform(String.valueOf(sysMap.resultMapToRecord(r).getTime())));
                    list.add(sysMap.resultMapToRecord(r).getEid());
                    System.out.println(list);
                    list.clear();
                    /*for (Cell cell : r.rawCells()) {
                        System.out.println(
                                Bytes.toString(r.getRow())+"##"
                                        +Bytes.toString(CellUtil.cloneQualifier(cell))+"##"
                                        +Bytes.toString(CellUtil.cloneValue(cell))+"\n");
                    }*/
                }
                scanner.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        else System.out.println("table not exits");

    }


    public static void main(String[] args) throws IOException {
        try {
            Sys inquire =new Sys();
            while (true) {
                String tablename = "rule";
                inquire.rowfilter(tablename);
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
