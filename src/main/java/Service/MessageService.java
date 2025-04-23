package Service;

import java.util.List;

import DAO.MessageDao;
import Model.Message;

public class MessageService {

    private static MessageService messageService_ = null;
    MessageDao messageDao;

    private MessageService(){
        this.messageDao = MessageDao.getInstance();
    }

    public static MessageService getInstance() {
        if(messageService_ == null) {
            messageService_ = new MessageService();
        }
        return messageService_;
    }

    public Message createMessage(Message message) throws Exception {
        if(message.getMessage_text() == null || message.getMessage_text().length() > 255 ||  message.getMessage_text().length() == 0) {
            throw new Exception("Create message error");
        } 
        return this.messageDao.createMessage(message);
    }

    public List<Message> getAllMessage() throws Exception {
        return this.messageDao.getAllMessage();
    }

    public Message getMessageById(int id) throws Exception {
        return this.messageDao.getMessageById(id);
    }

    public Message patchMessage(int id, Message message) throws Exception {
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
        return this.messageDao.getAllMessagesFromUser(accountId);
    }

}
