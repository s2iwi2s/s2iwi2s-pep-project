package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);

        ObjectMapper om = new ObjectMapper();

        AccountService as = new AccountService();
        app.post("register", ctx -> this.createAccount(ctx, as, om));
        app.post("login", ctx -> this.login(ctx, as, om));
        
        MessageService ms = new MessageService();
        app.post("messages", ctx -> this.createMessage(ctx, ms, om));
        app.get("messages", ctx -> this.getAllMessage(ctx, ms, om));
        app.get("messages/{message_id}", ctx -> this.getMessageById(ctx, ms, om));
        app.patch("messages/{message_id}", ctx -> this.patchMessage(ctx, ms, om));
        app.delete("messages/{message_id}", ctx -> this.deleteMessageById(ctx, ms, om));

        
        app.get("accounts/{accountId}/messages", ctx -> this.getAllMessagesFromUser(ctx, ms, om));

        return app;
    }

    private void getAllMessagesFromUser(Context ctx, MessageService ms, ObjectMapper om) {
        try {
            String accountId = ctx.pathParam("accountId");
            List<Message> listRet = ms.getAllMessagesFromUser(Integer.parseInt(accountId));

            ctx.status(200);
            ctx.json(listRet);
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(400);
        }
    }

    private void login(Context ctx, AccountService as, ObjectMapper om) throws JsonProcessingException {
        String jsonString = ctx.body();
        Account account = om.readValue(jsonString, Account.class);
        
        try {
            Account accountRet = as.login(account);

            ctx.status(200);
            ctx.json(accountRet);
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(401);
        }
    }

    private void createAccount(Context ctx, AccountService as, ObjectMapper om) throws JsonProcessingException {
        String jsonString = ctx.body();
        Account account = om.readValue(jsonString, Account.class);
        
        try {
            Account accountRet = as.createAccount(account);

            ctx.status(200);
            ctx.json(accountRet);
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(400);
        }
        
    }

    private void deleteMessageById(Context ctx, MessageService ms, ObjectMapper om) {
        try {
            String id = ctx.pathParam("message_id");
            Message message = ms.deleteMessageById(Integer.parseInt(id));
            ctx.json(message); 
        } catch (Exception e) {
            e.printStackTrace();
        }
        ctx.status(200);
    }

    private void patchMessage(Context ctx, MessageService ms, ObjectMapper om) throws JsonProcessingException {
        String jsonString = ctx.body();
        Message message = om.readValue(jsonString, Message.class);
        
        try {
            String id = ctx.pathParam("message_id");
            Message messageRet = ms.patchMessage(Integer.parseInt(id), message);
            ctx.status(200);
            ctx.json(messageRet);
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(400);
        }
    }

    private void getMessageById(Context ctx, MessageService ms, ObjectMapper om) {
        System.out.println("*** getMessageById");
        try {
            String id = ctx.pathParam("message_id");
            Message message = ms.getMessageById(Integer.parseInt(id));
            ctx.json(message); 
        } catch (Exception e) {
            e.printStackTrace();
        }
        ctx.status(200);
    }

    private void getAllMessage(Context ctx, MessageService ms, ObjectMapper om) {
        System.out.println("*** getAllMessage");
        try {
            List<Message> listRet = ms.getAllMessage();

            ctx.status(200);
            ctx.json(listRet);
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(400);
        }
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void createMessage(Context ctx, MessageService ms, ObjectMapper om) throws JsonProcessingException {
        System.out.println("*** createMessage");
        String jsonString = ctx.body();
        Message message = om.readValue(jsonString, Message.class);
        
        try {
            Message messageRet = ms.createMessage(message);

            ctx.status(200);
            ctx.json(messageRet);
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(400);
        }
    }


}