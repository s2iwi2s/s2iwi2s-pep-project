package Service;

import java.util.List;

import DAO.MessageDao;
import Model.Message;

public class MessageService {

    MessageDao messageDao;

    public MessageService(){
        this.messageDao = new MessageDao();
    }

    public Message createMessage(Message message) throws Exception {
        System.out.println(String.format("createMessage: %s, %d", message.getMessage_text(), message.getMessage_id()));
        if(message.getMessage_text() == null || message.getMessage_text().length() > 255 ||  message.getMessage_text().length() == 0) {
            throw new Exception("Create message error");
        } 
        return this.messageDao.createMessage(message);
    }

    public List<Message> getAllMessage() throws Exception {
        System.out.println("***** > getAllMessage Service");
        return this.messageDao.getAllMessage();
    }

    public Message getMessageById(int id) throws Exception {
        System.out.println("***** > getMessageById Service");
        return this.messageDao.getMessageById(id);
    }

    public Message patchMessage(int id, Message message) throws Exception {
        System.out.println("***** > patchMessage Service");
        if(message.getMessage_text() == null || message.getMessage_text().length() > 255 ||  message.getMessage_text().length() == 0) {
            throw new Exception("Patch message error");
        }
        if(this.messageDao.getMessageById(id) == null) {
            throw new Exception("Message not found error");
        }
        return this.messageDao.patchMessage(id, message);
    }

    public Message deleteMessageById(int id) throws Exception {
        Message message = this.messageDao.getMessageById(id);
        if(message == null) {
            throw new Exception("Message not found error");
        }
        this.messageDao.deleteMessageById(id);
        return message;
    }

    public List<Message> getAllMessagesFromUser(int accountId) throws Exception {
        System.out.println("***** > getAllMessagesFromUser Service");
        return this.messageDao.getAllMessagesFromUser(accountId);
    }

}
