import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class FilterOnRowKeys    {

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


            Filter filter = new RowFilter(CompareOperator.EQUAL,
                    new BinaryComparator(Bytes.toBytes("3")));

            Scan userScan = new Scan();
            userScan.setFilter(filter);

            scanResult = table.getScanner(userScan);

            printResults(scanResult);

            filter = new RowFilter(CompareOperator.LESS,
                    new BinaryComparator(Bytes.toBytes("2")));
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
