package aiss.gitlabminer.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@JsonInclude(JsonInclude.Include.ALWAYS)
@JsonPropertyOrder({
    "id",
    "iid",
    "title",
    "description",
    "state",
    "created_at",
    "updated_at",
    "closed_at",
    "labels",
    "author",
    "assignee",
    "upvotes",
    "downvotes",
    "web_url",
    "comments"

})
@Generated("jsonschema2pojo")
public class Issue {

    @JsonProperty("id")
    private String id;
    @JsonProperty("iid")
    private String refId;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("state")
    private String state;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("updated_at")
    private String updatedAt;
    @JsonProperty("closed_at")
    private String closedAt;
    @JsonProperty("labels")
    private List<String> labels;
    @JsonProperty("author")
    private User author;
    @JsonProperty("assignee")
    private User assignee;
    @JsonProperty("upvotes")
    private Integer upvotes;
    @JsonProperty("downvotes")
    private Integer downvotes;
    @JsonProperty("web_url")
    private String webUrl;
    @JsonProperty("comments")
    private List<Comment> comments;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("iid")
    public String getRefId() {
        return refId;
    }

    @JsonProperty("iid")
    public void setRefId(String refId) {
        this.refId = refId;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("state")
    public String getState() {
        return state;
    }

    @JsonProperty("state")
    public void setState(String state) {
        this.state = state;
    }

    @JsonProperty("created_at")
    public String getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("created_at")
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("updated_at")
    public String getUpdatedAt() {
        return updatedAt;
    }

    @JsonProperty("updated_at")
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @JsonProperty("closed_at")
    public String getClosedAt() {
        return closedAt;
    }

    @JsonProperty("closed_at")
    public void setClosedAt(String closedAt) {
        this.closedAt = closedAt;
    }

    @JsonProperty("labels")
    public List<String> getLabels() {
        return labels;
    }

    @JsonProperty("labels")
    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    @JsonProperty("upvotes")
    public Integer getUpvotes() {
        return upvotes;
    }

    @JsonProperty("upvotes")
    public void setUpvotes(Integer upvotes) {
        this.upvotes = upvotes;
    }

    @JsonProperty("downvotes")
    public Integer getDownvotes() {
        return downvotes;
    }

    @JsonProperty("downvotes")
    public void setDownvotes(Integer downvotes) {
        this.downvotes = downvotes;
    }
    @JsonProperty("author")
    public User getAuthor() {
        return author;
    }
    @JsonProperty("author")
    public void setAuthor(User author) {
        this.author = author;
    }
    @JsonProperty("assignee")
    public User getAssignee() {
        return assignee;
    }
    @JsonProperty("assignee")
    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    @JsonProperty("web_url")
    public String getWebUrl() {
        return webUrl;
    }
    @JsonProperty("web_url")
    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }
    @JsonProperty("comments")
    public List<Comment> getComments() {
        return comments;
    }
    @JsonProperty("comments")
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }






    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(",\n");
        sb.append("refId");
        sb.append('=');
        sb.append(((this.refId == null)?"<null>":this.refId));
        sb.append(",\n");
        sb.append("title");
        sb.append('=');
        //sb.append(((this.title == null)?"<null>":this.title));
        sb.append(",\n");
        sb.append("description");
        sb.append('=');
        //sb.append(((this.description == null)?"<null>":this.description));
        sb.append(",\n");
        sb.append("state");
        sb.append('=');
        sb.append(((this.state == null)?"<null>":this.state));
        sb.append(",\n");
        sb.append("createdAt");
        sb.append('=');
        sb.append(((this.createdAt == null)?"<null>":this.createdAt));
        sb.append(",\n");
        sb.append("updatedAt");
        sb.append('=');
        sb.append(((this.updatedAt == null)?"<null>":this.updatedAt));
        sb.append(",\n");
        sb.append("closedAt");
        sb.append('=');
        sb.append(((this.closedAt == null)?"<null>":this.closedAt));
        sb.append(",\n");
        sb.append("labels");
        sb.append('=');
        sb.append(((this.labels == null)?"<null>":this.labels));
        sb.append(",\n");
        sb.append("upvotes");
        sb.append('=');
        sb.append(((this.upvotes == null)?"<null>":this.upvotes));
        sb.append(",\n");
        sb.append("downvotes");
        sb.append('=');
        sb.append(((this.downvotes == null)?"<null>":this.downvotes));
        sb.append(",\n");
        sb.append("author");
        sb.append('=');
        sb.append(((this.author == null)?"<null>":this.author));
        sb.append(",\n");
        sb.append("assignee");
        sb.append('=');
        sb.append(((this.assignee == null)?"<null>":this.assignee));
        sb.append(",\n");
        sb.append("web_url");
        sb.append('=');
        sb.append(((this.webUrl == null)?"<null>":this.webUrl));
        sb.append(",\n");
        sb.append("comments");
        sb.append('=');
        sb.append(((this.comments == null)?"<null>":this.comments));
        sb.append(",\n");
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
