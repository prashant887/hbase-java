import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimpleDelete {

    private static byte[] PERSONAL_CF = Bytes.toBytes("personal");
    private static byte[] PROFESSIONAL_CF = Bytes.toBytes("professional");

    private static byte[] GENDER_COLUMN = Bytes.toBytes("gender");
    private static byte[] FIELD_COLUMN = Bytes.toBytes("field");
    public static void main(String[] args) throws IOException {

        Configuration configuration= HBaseConfiguration.create();

        Connection connection = ConnectionFactory.createConnection(configuration);
        Table table = null;

        try {
            table = connection.getTable(TableName.valueOf("census"));

            Delete delete = new Delete(Bytes.toBytes("1"));

            delete.addColumn(PERSONAL_CF, GENDER_COLUMN);
            delete.addColumn(PROFESSIONAL_CF, FIELD_COLUMN);

            table.delete(delete);

        }
        finally {
            if (table != null) {
                table.close();
            }
            connection.close();
        }

    }
}
