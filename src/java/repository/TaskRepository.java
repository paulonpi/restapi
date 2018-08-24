package repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;
import model.Task;

/**
 *
 * @author paulo
 */
public class TaskRepository {

    public TaskRepository() {
    }
    
    public int insert(Task task) throws SQLException, Exception {
        String sql = "INSERT INTO task(title, status, description) VALUES (?,?,?)";
        PreparedStatement pst = MySqlConnection.getPreparedStatement(sql, new String[]{"ID"});
        
        try {
            pst.setString(1, task.getTitle());
            pst.setString(2, task.getStatus());
            pst.setString(3, task.getDescription());
            
            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            
            if(rs.next())
                return rs.getInt((1));

        } catch (SQLException ex) {
            throw new Exception(ex);
        }
        
        return 0;
    }
    
    public boolean update(Task task) throws SQLException, Exception {
        String sql = "UPDATE task SET "
                + "title=?,status=?,description=?,editAt=?,removeAT=?,doneAt=? "
                + "where ID=?";
        
        PreparedStatement pst = MySqlConnection.getPreparedStatement(sql, new String[]{"ID"});
        
        try {
            pst.setString(1, task.getTitle());
            pst.setString(1, task.getStatus());
            pst.setString(2, task.getDescription());
            pst.setTimestamp(3, task.getEditAt());
            pst.setTimestamp(4, task.getRemoveAt());
            pst.setTimestamp(6, task.getDoneAt());
            pst.setInt(7, task.getID());
            
            if(pst.executeUpdate()>0)
                return true;
            
        } catch (SQLException ex) {
            throw new Exception(ex);
        }
        
        return false;
    }

    public boolean delete(Task task) throws SQLException, Exception
    {
        String sql = "DELETE FROM task where ID=?";
        
        PreparedStatement pst = MySqlConnection.getPreparedStatement(sql, new String[]{"ID"});
        
        try {
            pst.setInt(1, task.getID());

            if(pst.executeUpdate()>0)
                return true;
            
        } catch (SQLException ex) {
            throw new Exception(ex);
        }
        
        return false;
    }
    
    public List<Task> list(String desc) throws SQLException, Exception
    {
        String sql = "SELECT * FROM task WHERE status NOT LIKE 'deleted'";
        
        if(!desc.contains("*"))
            sql += " AND description LIKE '%" + desc + "%'";
        
        List<Task> taskList = new ArrayList<>();
        
        PreparedStatement pst = MySqlConnection.getPreparedStatement(sql, new String[]{"ID"});
        
        try {
           
            ResultSet res = pst.executeQuery();
            while(res.next())
            {
                Task task = new Task();
                task.setID(res.getInt("ID"));
                task.setTitle(res.getString("title"));
                task.setStatus(res.getString("status"));
                task.setDescription(res.getString("description"));
                task.setCreatAt(res.getTimestamp("creatAt"));
                task.setEditAt(res.getTimestamp("editAt"));
                task.setRemoveAt(res.getTimestamp("removeAt"));
                task.setDoneAt(res.getTimestamp("doneAt"));
                
                taskList.add(task);
            }
            
        } catch (SQLException ex) {
            throw new Exception(ex);
        }
        
        return taskList;
    }
    
    public Task getTask(Task task) throws SQLException, Exception
    {
        String sql = "SELECT * FROM task WHERE ID=?";
        Task item = null;
        
        PreparedStatement pst = MySqlConnection.getPreparedStatement(sql, new String[]{"ID"});
        
        try {
            pst.setInt(1, task.getID());
            ResultSet res = pst.executeQuery();
            
            if(res.next())
            {
                item = new Task();
                item.setID(res.getInt("ID"));
                item.setTitle(res.getString("title"));
                item.setStatus(res.getString("status"));
                item.setDescription(res.getString("description"));
                item.setCreatAt(res.getTimestamp("creatAt"));
                item.setEditAt(res.getTimestamp("editAt"));
                item.setRemoveAt(res.getTimestamp("removeAt"));
                item.setDoneAt(res.getTimestamp("doneAt"));
            }
        } catch (SQLException ex) {
            throw new Exception(ex);
        }
        
        return item;
    }
}
