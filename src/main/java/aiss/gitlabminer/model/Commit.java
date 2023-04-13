package aiss.gitlabminer.model;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "title",
    "message",
    "author_name",
    "author_email",
    "authored_date",
    "committer_name",
    "committer_email",
    "committed_date",
    "web_url",
    "project"
})


@Generated("jsonschema2pojo")

public class Commit {
    @JsonProperty("id")
    private String id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("message")
    private String message;
    @JsonProperty("author_name")
    private String authorName;
    @JsonProperty("author_email")
    private String authorEmail;
    @JsonProperty("authored_date")
    private String authoredDate;
    @JsonProperty("committer_name")
    private String committerName;
    @JsonProperty("committer_email")
    private String committerEmail;
    @JsonProperty("committed_date")
    private String committedDate;
    @JsonProperty("web_url")
    private String webUrl;
    @JsonProperty("project")
    private Project project;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty("author_name")
    public String getAuthorName() {
        return authorName;
    }

    @JsonProperty("author_name")
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    @JsonProperty("author_email")
    public String getAuthorEmail() {
        return authorEmail;
    }

    @JsonProperty("author_email")
    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    @JsonProperty("authored_date")
    public String getAuthoredDate() {
        return authoredDate;
    }

    @JsonProperty("authored_date")
    public void setAuthoredDate(String authoredDate) {
        this.authoredDate = authoredDate;
    }

    @JsonProperty("committer_name")
    public String getCommitterName() {
        return committerName;
    }

    @JsonProperty("committer_name")
    public void setCommitterName(String committerName) {
        this.committerName = committerName;
    }

    @JsonProperty("committer_email")
    public String getCommitterEmail() {
        return committerEmail;
    }

    @JsonProperty("committer_email")
    public void setCommitterEmail(String committerEmail) {
        this.committerEmail = committerEmail;
    }

    @JsonProperty("committed_date")
    public String getCommittedDate() {
        return committedDate;
    }

    @JsonProperty("committed_date")
    public void setCommittedDate(String committedDate) {
        this.committedDate = committedDate;
    }

    @JsonProperty("web_url")
    public String getWebUrl() {
        return webUrl;
    }

    @JsonProperty("web_url")
    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    @JsonProperty("project")
    public Project getProject() {
        return project;
    }
    @JsonProperty("project")
    public void setProject(Project project) {
        this.project = project;
    }




    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n[id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(",\n");
        sb.append("title");
        sb.append('=');
        //sb.append(((this.title == null)?"<null>":this.title));
        sb.append(",\n");
        sb.append("message");
        sb.append('=');
        //sb.append(((this.message == null)?"<null>":this.message));
        sb.append(",\n");
        sb.append("authorName");
        sb.append('=');
        sb.append(((this.authorName == null)?"<null>":this.authorName));
        sb.append(",\n");
        sb.append("authorEmail");
        sb.append('=');
        sb.append(((this.authorEmail == null)?"<null>":this.authorEmail));
        sb.append(",\n");
        sb.append("authoredDate");
        sb.append('=');
        sb.append(((this.authoredDate == null)?"<null>":this.authoredDate));
        sb.append(",\n");
        sb.append("committerName");
        sb.append('=');
        sb.append(((this.committerName == null)?"<null>":this.committerName));
        sb.append(",\n");
        sb.append("committerEmail");
        sb.append('=');
        sb.append(((this.committerEmail == null)?"<null>":this.committerEmail));
        sb.append(",\n");
        sb.append("committedDate");
        sb.append('=');
        sb.append(((this.committedDate == null)?"<null>":this.committedDate));
        sb.append(",\n");
        sb.append("webUrl");
        sb.append('=');
        sb.append(((this.webUrl == null)?"<null>":this.webUrl));
        sb.append(",\n");
        sb.append("project");
        sb.append('=');
        sb.append(((this.project == null)?"<null>":this.project));
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append("]\n");
        }
        return sb.toString();
    }

}
