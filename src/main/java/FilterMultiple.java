import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class FilterMultiple {

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

            SingleColumnValueFilter filter = new SingleColumnValueFilter(
                    Bytes.toBytes("personal"),
                    Bytes.toBytes("gender"),
                    CompareOperator.EQUAL,
                    new BinaryComparator(Bytes.toBytes("male")));

            filter.setFilterIfMissing(true);//If column gender is not found entire row is skipped , if false row is consider , only female is filtered

            Scan userScan = new Scan();
            userScan.setFilter(filter);

            scanResult = table.getScanner(userScan);

            printResults(scanResult);
            /*
            personal:name like Jones
             */

            filter = new SingleColumnValueFilter(
                    Bytes.toBytes("personal"),
                    Bytes.toBytes("name"),
                    CompareOperator.EQUAL,
                    new SubstringComparator("Jones"));

            userScan.setFilter(filter);
            scanResult = table.getScanner(userScan);

            printResults(scanResult);

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
