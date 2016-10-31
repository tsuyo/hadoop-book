import java.net.URI;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileUtil;

public class ListStatusWithFilter {

  public static void main(String[] args) throws Exception {
    String uri = args[0];
    Configuration conf = new Configuration();
    FileSystem fs = FileSystem.get(URI.create(uri), conf);

    FileStatus[] status = fs.globStatus(new Path("/date/2007/*/*"),
      new RegexExcludePathFilter("^.*/date/2007/12/31$"));
    Path[] listedPaths = FileUtil.stat2Paths(status);
    for (Path p: listedPaths) {
      System.out.println(p);
    }
  }
}
