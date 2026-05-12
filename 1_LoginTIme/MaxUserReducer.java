import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class MaxUserReducer extends Reducer<Text, IntWritable, Text, IntWritable>
{
    private int globalMax = 0;
    private String maxUser = "";

    public void reduce(Text key, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException
    {
        int total = 0;

        for(IntWritable val : values)
        {
            total += val.get();
        }

        if(total > globalMax)
        {
            globalMax = total;
            maxUser = key.toString();
        }
    }

    @Override
    protected void cleanup(Context context)
            throws IOException, InterruptedException
    {
        context.write(new Text("MAX_USER : " + maxUser),
                new IntWritable(globalMax));
    }
}
