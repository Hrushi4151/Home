import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;

public class WordMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
	
	private final static IntWritable  one = new IntWritable(1);
	private Text word = new Text();
	
	
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException{
		
		String line = value.toString().toLowerCase();
		
		String words[] = line.split("\\s+");
		
		
		for(String w : words){
			
		    word.set(w);
		    context.write(word, one);
		}		
		
	}

}
