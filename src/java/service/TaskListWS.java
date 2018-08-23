package service;

import com.google.gson.Gson;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import model.Task;
import repository.TaskRepository;

/**
 * REST Web Service
 *
 * @author paulo
 */
@Path("task")
public class TaskListWS {

    @Context
    private UriInfo context;

    public TaskListWS() {}
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        Gson g = new Gson();
        return g.toJson("Test WS RESTFULL");
    }

    // get single task by id
    @GET
    @Produces(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("get/{ID}")
    public Response getTask(@PathParam("ID") int ID) throws Exception{
        Task t = new Task();
        t.setID(ID);
        
        TaskRepository taskRepo = new TaskRepository();
        
        try {
            t = taskRepo.getTask(t);
        } catch (SQLException ex) {
            return Response.status(500).entity(ex.toString()).build();
        }
        
        Gson g = new Gson();
        
        return Response.status(200)
            .entity(g.toJson(t))
                .build();
    }
    
    //get list of tasks
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("list")
    public Response listTaks() throws Exception
    {
        List<Task> taskList = null;
        TaskRepository taskRepo = new TaskRepository();
        
        try {
            taskList = taskRepo.list();
        } catch (SQLException ex) {
            return Response.status(500).entity(ex.toString()).build();
        }
        
        Gson g = new Gson();
        
        return Response.status(200)
            .entity(g.toJson(taskList))
                .build();
    }

    //update task
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("update")
    public Response updateTask(
            @FormParam("ID") int id,
            @FormParam("title") String title,
            @FormParam("status") String status,
            @FormParam("description") String description,
            @FormParam("editAt") Timestamp editAt,
            @FormParam("removeAt") Timestamp removeAt,
            @FormParam("doneAt") Timestamp doneAt) throws Exception {
        
        Task task = new Task(id, title, status, description, editAt, removeAt, doneAt);
        TaskRepository taskRepo = new TaskRepository();
        
        try {
            taskRepo.update(task);
        } catch (SQLException ex) {
            return Response.status(500).entity(ex.toString()).build();
        }

        Gson g = new Gson();
        
        return Response.status(200)
            .entity(g.toJson(task))
                .build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("insert")
    public Response insertTask(
            @FormParam("title") String title,
            @FormParam("description") String description) throws Exception {
        
        Task task = new Task(title, "Aberto", description);
        TaskRepository taskRepo = new TaskRepository();
        
        try {
            int taskId = taskRepo.insert(task);
            
            if(taskId > 0){
                task.setID(taskId);
                task.setCreatAt(new Timestamp(System.currentTimeMillis()));
                
                Gson g = new Gson();
                return Response.status(200)
                    .entity(g.toJson(task))
                        .build();
            }
            
        } catch (SQLException ex) {
            return Response.status(500).entity(ex.toString()).build();
        }

        return Response.status(500).build();
    }
}
