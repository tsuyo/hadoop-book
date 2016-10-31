import java.io.*;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hdfs.MiniDFSCluster;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class CoherencyModelTest {
  private MiniDFSCluster cluster; // use an in-process HDFS cluster for testing
  private FileSystem fs;

  @Before
  public void setUp() throws IOException {
    Configuration conf = new Configuration();
    if (System.getProperty("test.build.data") == null) {
      System.setProperty("test.build.data", "/tmp");
    }
    cluster = new MiniDFSCluster.Builder(conf).build();
    fs = cluster.getFileSystem();
  }

  @After
  public void tearDown() throws IOException {
    if (fs != null) { fs.close(); }
    if (cluster != null) { cluster.shutdown(); }
  }

  @Test
  public void fileExistsImmediatelyAfterCreation() throws IOException {
    Path p = new Path("p");
    fs.create(p);
    assertThat(fs.exists(p), is(true));
    assertThat(fs.delete(p, true), is(true));
  }

  @Test
  public void fileContentIsNotVisibleAfterFlush() throws IOException {
    Path p = new Path("p");
    OutputStream out = fs.create(p);
    out.write("content".getBytes("UTF-8"));
    out.flush();
    assertThat(fs.getFileStatus(p).getLen(), is(0L));
    out.close();
    assertThat(fs.delete(p, true), is(true));
  }

  @Test
  public void fileContentIsVisibleAfterHFlush() throws IOException {
    Path p = new Path("p");
    FSDataOutputStream out = fs.create(p);
    out.write("content".getBytes("UTF-8"));
    out.hflush();
    assertThat(fs.getFileStatus(p).getLen(), is(((long) "content".length())));
    out.close();
    assertThat(fs.delete(p, true), is(true));
  }

  @Test
  public void fileContentIsVisibleAfterHSync() throws IOException {
    Path p = new Path("p");
    FSDataOutputStream out = fs.create(p);
    out.write("content".getBytes("UTF-8"));
    out.hsync();
    assertThat(fs.getFileStatus(p).getLen(), is(((long) "content".length())));
    out.close();
    assertThat(fs.delete(p, true), is(true));
  }

  @Test
  public void localFileContentIsVisibleAfterFlushAndSync() throws IOException {
    File localFile = File.createTempFile("tmp", "");
    assertThat(localFile.exists(), is(true));
    FileOutputStream out = new FileOutputStream(localFile);
    out.write("content".getBytes("UTF-8"));
    out.flush();
    out.getFD().sync();
    assertThat(localFile.length(), is(((long) "content".length())));
    out.close();
    assertThat(localFile.delete(), is(true));
  }

  @Test
  public void fileContentIsVisibleAfterClose() throws IOException {
    Path p = new Path("p");
    OutputStream out = fs.create(p);
    out.write("content".getBytes("UTF-8"));
    out.close();
    assertThat(fs.getFileStatus(p).getLen(), is(((long) "content".length())));
    // out.close();
    assertThat(fs.delete(p, true), is(true));
  }

}
