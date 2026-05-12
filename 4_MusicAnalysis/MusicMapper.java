import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MusicMapper extends Mapper<Object, Text, Text, Text> {

    public void map(Object key, Text value, Context context)
            throws IOException, InterruptedException {

        String line = value.toString();

        // Skip header
        if(line.contains("UserId"))
            return;

        String[] fields = line.split(",");

        String trackId = fields[1];
        String radio = fields[3];
        String skip = fields[4];

        context.write(new Text(trackId),
                      new Text(radio + "," + skip));
    }
}