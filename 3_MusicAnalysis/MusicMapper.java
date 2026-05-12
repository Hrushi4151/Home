import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MusicMapper extends Mapper<Object, Text, Text, Text> {

    private boolean isHeader = true;

    public void map(Object key, Text value, Context context)
            throws IOException, InterruptedException {

        String line = value.toString();

        // Skip CSV header
        if (isHeader) {
            isHeader = false;
            if (line.contains("UserId")) {
                return;
            }
        }

        String[] fields = line.split(",");

        if (fields.length >= 3) {

            String userId = fields[0].trim();
            String trackId = fields[1].trim();
            String shared = fields[2].trim();

            // Output:
            // key = TrackId
            // value = UserId,Shared
            context.write(
                    new Text(trackId),
                    new Text(userId + "," + shared)
            );
        }
    }
}