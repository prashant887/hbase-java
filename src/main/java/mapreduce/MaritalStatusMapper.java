package mapreduce;

import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.io.IntWritable;

import java.io.IOException;

/*
ImmutableBytesWritable, IntWritable -> Writeable Interfce from Hadoop
Key of String Value if Int
ImmutableBytesWritable Wrapper around byte array ,o/p key type
IntWritable
 */
public class MaritalStatusMapper extends TableMapper<ImmutableBytesWritable, IntWritable>{
    public static final byte[] CF = "personal".getBytes();
    public static final byte[] MARITAL_STATUS = "marital_status".getBytes();

    private final IntWritable ONE = new IntWritable(1);
    private ImmutableBytesWritable key = new ImmutableBytesWritable();

    /*
    This method processes one rec at a time for Hbase
    and read as <row key,result> pair
     */
    public void map(ImmutableBytesWritable row, Result value, Context context)
            throws IOException, InterruptedException {
        key.set(value.getValue(CF, MARITAL_STATUS)); //Extacts the column value for Col Family , col i.e. personal:maritial_status and sets key

        context.write(key, ONE); //(<marital_status>,1)
    }

}
