import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FilterOnColumnValues {

    private static void printResults(ResultScanner scanResult) {
        System.out.println();
        System.out.println("Results: ");

        for (Result res : scanResult) {
            for (Cell cell : res.listCells()) {
                String row = new String(CellUtil.cloneRow(cell));
                String family = new String(CellUtil.cloneFamily(cell));
                String column = new String(CellUtil.cloneQualifier(cell));
                String value = new String(CellUtil.cloneValue(cell));

                System.out.println(row + " " + family + " " + column + " " + value);
            }
        }
    }
    public static void main(String[] args) throws IOException {
        Configuration configuration= HBaseConfiguration.create();

        Connection connection = ConnectionFactory.createConnection(configuration);

        Table table = null;
        ResultScanner scanResult = null;

        try {

            TableName tableName = TableName.valueOf("census");

             table= connection.getTable(tableName);

             /*
             Look for personal:gender=male
              */

            SingleColumnValueFilter maritalStatusFilter = new SingleColumnValueFilter(
                    Bytes.toBytes("personal"),
                    Bytes.toBytes("marital_status"),
                    CompareOperator.EQUAL,
                    new BinaryComparator(Bytes.toBytes("unmarried")));

            maritalStatusFilter.setFilterIfMissing(true);

            SingleColumnValueFilter genderFilter = new SingleColumnValueFilter(
                    Bytes.toBytes("personal"),
                    Bytes.toBytes("gender"),
                    CompareOperator.EQUAL,
                    new BinaryComparator(Bytes.toBytes("male")));

            genderFilter.setFilterIfMissing(true);
            /*
            FIlter recs both Men and Unmarried
             */

            List<Filter> filterList = new ArrayList<Filter>();
            filterList.add(maritalStatusFilter);
            filterList.add(genderFilter);

            FilterList filters = new FilterList(filterList);


            Scan userScan = new Scan();
            userScan.setFilter(filters);

            scanResult = table.getScanner(userScan);

            printResults(scanResult);
            /*
            personal:name like Jones
             */


        } finally {
            connection.close();
            if (table != null) {
                table.close();
            }
            if (scanResult != null) {
                scanResult.close();
            }
        }

    }
}
