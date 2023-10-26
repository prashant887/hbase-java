
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateTable {

    public static void main(String[] args) throws IOException {

        Configuration configuration= HBaseConfiguration.create();

        Connection connection = ConnectionFactory.createConnection(configuration);

        try {
            /*
            For all schema level operations admin is needed
             */
            Admin admin=connection.getAdmin();

            TableName tableName = TableName.valueOf("census");

            byte[] personal = "personal".getBytes();
            byte[] professional = "professional".getBytes();

            List<ColumnFamilyDescriptor> columns= new ArrayList<>();
            columns.add(ColumnFamilyDescriptorBuilder.of(personal));
            columns.add(ColumnFamilyDescriptorBuilder.of(professional));

            TableDescriptor tableDescriptor= TableDescriptorBuilder
                    .newBuilder(tableName)
                    .setColumnFamilies(columns)
                    .build();


            if (!admin.tableExists(tableName)) {
                System.out.print("Creating the census table. ");

                admin.createTable(tableDescriptor);

                System.out.println("Table Created.");

            } else {
                System.out.println("Table already exists");
            }

        }
        finally {
            connection.close();
        }

    }
}
