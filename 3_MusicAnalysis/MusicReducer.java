import java.io.IOException;
import java.util.HashSet;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class MusicReducer extends Reducer<Text, Text, Text, Text> {

    public void reduce(Text key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {

        HashSet<String> uniqueUsers = new HashSet<>();

        int shareCount = 0;

        for (Text val : values) {

            String[] data = val.toString().split(",");

            String userId = data[0];
            int shared = Integer.parseInt(data[1]);

            uniqueUsers.add(userId);

            if (shared == 1) {
                shareCount++;
            }
        }

        String result =
                "Unique Listeners: " + uniqueUsers.size()
                + ", Shares: " + shareCount;

        context.write(key, new Text(result));
    }
}