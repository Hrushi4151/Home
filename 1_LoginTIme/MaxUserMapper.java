import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MaxUserMapper extends Mapper<LongWritable, Text, Text, IntWritable>
{
    private Text ip = new Text();
    private IntWritable durationWritable = new IntWritable();

    public void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException
    {
        try
        {
            String line = value.toString();
            String[] fields = line.split(",");

            // Ignore invalid rows
            if(fields.length < 8)
                return;

            // IP Address
            String userIP = fields[1].trim();

            // Login and Logout Time
            String loginTime = fields[5].trim();
            String logoutTime = fields[7].trim();

            SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy H:mm");

            Date login = sdf.parse(loginTime);
            Date logout = sdf.parse(logoutTime);

            // Duration in minutes
            long diff = logout.getTime() - login.getTime();
            int minutes = (int)(diff / (1000 * 60));

            ip.set(userIP);
            durationWritable.set(minutes);

            context.write(ip, durationWritable);
        }
        catch(Exception e)
        {
            // Ignore parsing errors
        }
    }
}

