import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class MusicReducer extends Reducer<Text, Text, Text, Text> {

    public void reduce(Text key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {

        int radioCount = 0;
        int skipCount = 0;

        for(Text val : values) {

            String[] data = val.toString().split(",");

            int radio = Integer.parseInt(data[0]);
            int skip = Integer.parseInt(data[1]);

            radioCount += radio;
            skipCount += skip;
        }

        context.write(key,
                new Text("Radio: " + radioCount +
                         " Skip: " + skipCount));
    }
}