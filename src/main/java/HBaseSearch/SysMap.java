package HBaseSearch;

import data.Record;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.List;

public class SysMap {
    public Record resultMapToRecord(Result result){
        Record record = new Record();
        //1.分解行键
        String[] rowKey = Bytes.toString(result.getRow()).split("##");
        record.setPlaceId(Integer.parseInt(rowKey[0]));
        record.setTime(Long.parseLong(rowKey[1]));
        //2.解析所有的列信息
        List<Cell> cellList =  result.listCells();
        for(Cell cell : cellList){
            String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
            String value = Bytes.toString(CellUtil.cloneValue(cell));
            switch(qualifier){
                case "address" : record.setAddress(value); break;
                case "eid" : record.setEid(value); break;
                case "longitude" : record.setLongitude(Double.parseDouble(value)); break;
                case "latitude" : record.setLatitude(Double.parseDouble(value)); break;

            }

        }
        return record;
    }


}
