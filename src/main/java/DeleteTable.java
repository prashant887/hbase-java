
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeleteTable {

    public static void main(String[] args) throws IOException {

        Configuration configuration= HBaseConfiguration.create();

        Connection connection = ConnectionFactory.createConnection(configuration);

        try {
            /*
            For all schema level operations admin is needed
             */
            Admin admin=connection.getAdmin();

            TableName tableName = TableName.valueOf("census");



            if (!admin.tableExists(tableName)) {
                System.out.print("Table exists, Deleting.. ");

                admin.disableTable(tableName);
                admin.deleteTable(tableName);

                System.out.println(" Done.");

            } else {
                System.out.println("Table does not exists");
            }

        }
        finally {
            connection.close();
        }

    }
}
