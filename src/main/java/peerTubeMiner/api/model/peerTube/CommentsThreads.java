
package peerTubeMiner.api.model.peerTube;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CommentsThreads {

    @JsonProperty("total")
    private Integer total;
    @JsonProperty("data")
    private List<CommentData> data;
    @JsonProperty("totalNotDeletedComments")
    private Integer totalNotDeletedComments;

    @JsonProperty("total")
    public Integer getTotal() {
        return total;
    }

    @JsonProperty("total")
    public void setTotal(Integer total) {
        this.total = total;
    }

    @JsonProperty("data")
    public List<CommentData> getData() {
        return data;
    }

    @JsonProperty("data")
    public void setData(List<CommentData> data) {
        this.data = data;
    }

    @JsonProperty("totalNotDeletedComments")
    public Integer getTotalNotDeletedComments() {
        return totalNotDeletedComments;
    }

    @JsonProperty("totalNotDeletedComments")
    public void setTotalNotDeletedComments(Integer totalNotDeletedComments) {
        this.totalNotDeletedComments = totalNotDeletedComments;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(CommentsThreads.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("total");
        sb.append('=');
        sb.append(((this.total == null)?"<null>":this.total));
        sb.append(',');
        sb.append("data");
        sb.append('=');
        sb.append(((this.data == null)?"<null>":this.data));
        sb.append(',');
        sb.append("totalNotDeletedComments");
        sb.append('=');
        sb.append(((this.totalNotDeletedComments == null)?"<null>":this.totalNotDeletedComments));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
