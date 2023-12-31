
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimplePut {

    private static byte[] PERSONAL_CF = "personal".getBytes();
    private static byte[] PROFESSIONAL_CF = Bytes.toBytes("professional");

    private static byte[] NAME_COLUMN = Bytes.toBytes("name");
    private static byte[] GENDER_COLUMN = Bytes.toBytes("gender");
    private static byte[] MARITAL_STATUS_COLUMN = Bytes.toBytes("marital_status");

    private static byte[] EMPLOYED_COLUMN = Bytes.toBytes("employed");
    private static byte[] FIELD_COLUMN = Bytes.toBytes("field");
    public static void main(String[] args) throws IOException {

        Configuration configuration= HBaseConfiguration.create();

        Connection connection = ConnectionFactory.createConnection(configuration);

        try {
            /*
            For all schema level operations admin is needed
             */

            TableName tableName = TableName.valueOf("census");

           Table table= connection.getTable(tableName);


            Put put1 = new Put(Bytes.toBytes("1"));//setting Row Id

            put1.addColumn(PERSONAL_CF, NAME_COLUMN, Bytes.toBytes("Mike Jones"));
            put1.addColumn(PERSONAL_CF, GENDER_COLUMN, Bytes.toBytes("male"));
            put1.addColumn(PERSONAL_CF, MARITAL_STATUS_COLUMN, Bytes.toBytes("unmarried"));
            put1.addColumn(PROFESSIONAL_CF, EMPLOYED_COLUMN, Bytes.toBytes("yes"));
            put1.addColumn(PROFESSIONAL_CF, FIELD_COLUMN, Bytes.toBytes("construction"));

            /*
            Cell cell=CellBuilderFactory
                    .create(CellBuilderType.DEEP_COPY).
                    setFamily(PERSONAL_CF)
                    .setQualifier(NAME_COLUMN).
                    setValue(Bytes.toBytes("Mike Jones")).build();

            put1.add(cell);

             */


            table.put(put1);


            System.out.println("Inserted row for Mike Jones");

            Put put2 = new Put(Bytes.toBytes("2"));

            put2.addColumn(PERSONAL_CF, NAME_COLUMN, Bytes.toBytes("Hillary Clinton"));
            put2.addColumn(PERSONAL_CF, GENDER_COLUMN, Bytes.toBytes("female"));
            put2.addColumn(PERSONAL_CF, MARITAL_STATUS_COLUMN, Bytes.toBytes("married"));
            put2.addColumn(PROFESSIONAL_CF, FIELD_COLUMN, Bytes.toBytes("politics"));

            Put put3 = new Put(Bytes.toBytes("3"));

            put3.addColumn(PERSONAL_CF, NAME_COLUMN, Bytes.toBytes("Donald Trump"));
            put3.addColumn(PERSONAL_CF, GENDER_COLUMN, Bytes.toBytes("male"));
            put3.addColumn(PROFESSIONAL_CF, FIELD_COLUMN, Bytes.toBytes("politics, real estate"));

            List<Put> putList = new ArrayList<Put>();
            putList.add(put2);
            putList.add(put3);

            table.put(putList);

            System.out.println("Inserted rows for Hillary Clinton and Donald Trump");

            table.close();



        }
        finally {
            connection.close();
        }

    }
}
