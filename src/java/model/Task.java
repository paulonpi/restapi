
package model;

import java.sql.Timestamp;


/**
 *
 * @author paulo
 */
public class Task {
    
    private int ID;
    private String title;
    private String status;
    private String description;
    private Timestamp creatAt;
    private Timestamp editAt;
    private Timestamp removeAt;
    private Timestamp doneAt;

    public Task() {
    }

    public Task(String title, String status, String description) {
        this.title = title;
        this.status = status;
        this.description = description;
    }

    public Task(int ID, String title, String status, String description, Timestamp editAt, Timestamp removeAt, Timestamp doneAt) {
        this.ID = ID;
        this.title = title;
        this.status = status;
        this.description = description;
        this.creatAt = creatAt;
        this.editAt = editAt;
        this.removeAt = removeAt;
        this.doneAt = doneAt;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreatAt() {
        return creatAt;
    }

    public void setCreatAt(Timestamp creatAt) {
        this.creatAt = creatAt;
    }

    public Timestamp getEditAt() {
        return editAt;
    }

    public void setEditAt(Timestamp editAt) {
        this.editAt = editAt;
    }

    public Timestamp getRemoveAt() {
        return removeAt;
    }

    public void setRemoveAt(Timestamp removeAt) {
        this.removeAt = removeAt;
    }

    public Timestamp getDoneAt() {
        return doneAt;
    }

    public void setDoneAt(Timestamp doneAt) {
        this.doneAt = doneAt;
    }

        
    
}
