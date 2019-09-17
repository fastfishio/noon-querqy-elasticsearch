package querqy.elasticsearch.rewriterstore;

import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.common.xcontent.StatusToXContentObject;
import org.elasticsearch.common.xcontent.ToXContent;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.rest.RestStatus;

import java.io.IOException;

public class PutRewriteChainResponse extends ActionResponse implements StatusToXContentObject {

    private IndexResponse indexResponse;
    private NodesReloadRewriteChainResponse reloadResponse;

    protected PutRewriteChainResponse() {}

    public PutRewriteChainResponse(final IndexResponse indexResponse,
                                   final NodesReloadRewriteChainResponse reloadResponse) {
        this.indexResponse = indexResponse;
        this.reloadResponse = reloadResponse;
    }

    @Override
    public void readFrom(final StreamInput in) throws IOException {
        super.readFrom(in);
        indexResponse = new IndexResponse();
        indexResponse.readFrom(in);
        reloadResponse = new NodesReloadRewriteChainResponse(in);
    }

    @Override
    public void writeTo(final StreamOutput out) throws IOException {
        super.writeTo(out);
        indexResponse.writeTo(out);
        reloadResponse.writeTo(out);
    }

    @Override
    public RestStatus status() {
        return indexResponse.status();
    }

    @Override
    public XContentBuilder toXContent(final XContentBuilder builder, final ToXContent.Params params) throws IOException {

        builder.startObject();
        builder.field("put", indexResponse);
        builder.field("reloaded", reloadResponse);
        builder.endObject();
        return builder;
    }

    public IndexResponse getIndexResponse() {
        return indexResponse;
    }

    public NodesReloadRewriteChainResponse getReloadResponse() {
        return reloadResponse;
    }
}
