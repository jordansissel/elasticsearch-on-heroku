import org.elasticsearch.node.NodeBuilder;

public class ElasticSearch {
  public static void main(String[] args) {
    NodeBuilder nb = NodeBuilder.nodeBuilder();
    nb.local(true);
    nb.node();

    while (true) {
      try {
        Thread.currentThread().sleep(60000);
      } catch (Exception e) {
        /* ignore */
      }
    }
  }
}
