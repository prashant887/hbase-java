package mapreduce;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;

import java.io.IOException;

/*
It takes 3 params
ImmutableBytesWritable, IntWritable : I/P from Map's Output
ImmutableBytesWritable output of reduce
 */
public class MaritalStatusReducer extends
        TableReducer<ImmutableBytesWritable, IntWritable, ImmutableBytesWritable> {

    public static final byte[] CF = "marital_status".getBytes();
    public static final byte[] COUNT = "count".getBytes();

    public void reduce(ImmutableBytesWritable key, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {
        int sum = 0;
        //Sum all counts for given Key
        for (IntWritable val : values) {
            sum += val.get();
        }

        Put put = new Put(key.get());
        put.addColumn(CF, COUNT, Bytes.toBytes(Integer.toString(sum)));

        context.write(key, put);
    }

}
