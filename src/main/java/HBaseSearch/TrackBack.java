package HBaseSearch;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import hbase.HBaseConf;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
public class TrackBack {


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
        } catch (IOException e) { }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis()/1000;
    }


    public  static String geteid()
    {
        String eid;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while(true){
                System.out.println("请输入eid：");
                eid=in.readLine();
            }
        }
        catch (Exception e)
        {
            System.out.println("输入错误请重新输入");
            String a = geteid();
            return a;
        }
    }


    public void rowfilter(String tablename) throws IOException {
        List list = new ArrayList();
        java.lang.System.out.println("请输入开始时间：");
        long start = test();
        System.out.println("请输入结束时间：");
        long  end = test();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("请输入eid：");
        //deleteFile(path);
        String car = in.readLine();
        Connection connection = HBaseConf.getConnection();
        Admin admin = connection.getAdmin();
        TableName name = TableName.valueOf(tablename);

        if (admin.tableExists(name)){
            try {
                TableName tableName = TableName.valueOf(Bytes.toBytes(tablename));
                Table table = connection.getTable(tableName);
                //Get get = new Get(Bytes.toBytes(rowkeyvalue));
                Map map = new Map();
                //Filter filter1 = new RowFilter(CompareFilter.CompareOp.GREATER_OR_EQUAL,new BinaryComparator());
                Scan scan = new Scan();
                scan.setStartRow(Bytes.toBytes(start +"##" + car));
                scan.setStopRow(Bytes.toBytes(end +"##" + car));
                Filter filter = new RowFilter(CompareFilter.CompareOp.EQUAL,new RegexStringComparator(".*"+"##"+car));
                scan.setFilter(filter);

                ResultScanner scanner = table.getScanner(scan);

                for (Result r : scanner) {
                    list.add(map.resultMapToRecord(r).getAddress());
                    list.add(map.resultMapToRecord(r).getLongitude());
                    list.add(map.resultMapToRecord(r).getLatitude());
                    System.out.println(list);
                    list.clear();

                    /*for (Cell cell : r.rawCells()) {
                        System.out.println(
                            Bytes.toString(r.getRow())+"##"
                                        +Bytes.toString(CellUtil.cloneQualifier(cell))+"##"
                                       +Bytes.toString(CellUtil.cloneValue(cell))+"\n");
                        saveAsFileWriter(Bytes.toString(r.getRow())+"##"
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
        //connection.close();
        //admin.close();
    }


    public static void main(String[] args) throws IOException {
        try {
            //BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            TrackBack inquire =new TrackBack();
            while(true) {
                String tablename = "search";
                inquire.rowfilter(tablename);
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}