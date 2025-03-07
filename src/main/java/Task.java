import java.util.Date;
import java.util.UUID;

public class Task {
    private final String id = UUID.randomUUID().toString();
    private String description;
    private String status = "to-do";
    private final Date createdAt = new Date();
    private Date updatedAt = new Date();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }
}
