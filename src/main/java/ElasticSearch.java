import org.elasticsearch.node;

public class ElasticSearch {
  public static void main(String[] args) {
    NodeBuilder nb = NodeBuilder.nodeBuilder();
    nb.local(true);
    nb.node();

    while (true) {
      sleep(60);
    }
  }
}
