import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class MaxUserCSV {
        // Mapper
        public static class MyMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
                public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
                        try {
                                String line = value.toString();

                                // Skip CSV header
                                if (line.contains("Login Time"))
                                        return;

                                // Split CSV columns
                                String[] f = line.split(",");

                                if (f.length < 8)
                                        return;

                                // IP Address
                                String ip = f[1].trim();

                                // Login and Logout Time
                                String login = f[5].trim();
                                String logout = f[7].trim();

                                SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy H:mm");

                                Date d1 = sdf.parse(login);
                                Date d2 = sdf.parse(logout);

                                // Duration in minutes
                                int minutes = (int) ((d2.getTime() - d1.getTime())
                                                / (1000 * 60));

                                context.write(new Text(ip),new IntWritable(minutes));
                        } catch (Exception e) {
                        }
                }
        }

        // Reducer
        public static class MyReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
                int max = 0;
                String maxUser = "";

                public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
                        int total = 0;

                        for (IntWritable val : values) {
                                total += val.get();
                        }

                        if (total > max) {
                                max = total;
                                maxUser = key.toString();
                        }
                }

                protected void cleanup(Context context) throws IOException, InterruptedException {
                        context.write(new Text("MAX USER : " + maxUser),new IntWritable(max));
                }
        }

        // Driver
        public static void main(String[] args) throws Exception {
                Configuration conf = new Configuration();

                Job job = Job.getInstance(conf,
                                "CSV Log Analysis");

                job.setJarByClass(MaxUserCSV.class);

                job.setMapperClass(MyMapper.class);
                job.setReducerClass(MyReducer.class);

                job.setOutputKeyClass(Text.class);
                job.setOutputValueClass(IntWritable.class);

                // CSV input file
                FileInputFormat.addInputPath(job, new Path(args[0]));

                FileOutputFormat.setOutputPath(job, new Path(args[1]));

                System.exit(job.waitForCompletion(true) ? 0 : 1);
        }
}