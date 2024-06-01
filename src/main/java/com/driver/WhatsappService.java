package com.driver;

import java.util.*;
import org.springframework.stereotype.Service;

@Service
public class WhatsappService {

    WhatsappRepository whatsappRepository = new WhatsappRepository();

    public String createUser(String name, String mobile) throws Exception {
        String result = whatsappRepository.createUser(name, mobile);
        if(result == null) {
            throw new Exception("User already exists");
        }
        return result;
    }

    public Group createGroup(List<User> users){
        // The list contains at least 2 users where the first user is the admin.
        // If there are only 2 users, the group is a personal chat and the group name should be kept as the name of the second user(other than admin)
        // If there are 2+ users, the name of group should be "Group #count". For example, the name of first group would be "Group 1", second would be "Group 2" and so on.
        // Note that a personal chat is not considered a group and the count is not updated for personal chats.
        return whatsappRepository.createGroup(users);
    }

    public int createMessage(String content){
        // The 'i^th' created message has message id 'i'.
        return whatsappRepository.createMessage(content);
    }

    public int sendMessage(Message message, User sender, Group group) throws Exception{
        //Throw "Group does not exist" if the mentioned group does not exist
        //Throw "You are not allowed to send message" if the sender is not a member of the group
        if(!whatsappRepository.isPresent(group)){
            throw new Exception("Group does not exist");
        }
        List<User> users = whatsappRepository.getGroupUsersList(group);
        if(!users.contains(sender)){
            throw new Exception("You are not allowed to send message");
        }
        return  whatsappRepository.sendMessage(message, sender, group);
    }

    public String changeAdmin(User approver, User user, Group group) throws Exception{
        //Change the admin of the group to "user".
        //Throw "Group does not exist" if the mentioned group does not exist
        //Throw "Approver does not have rights" if the approver is not the current admin of the group
        //Throw "User is not a participant" if the user is not a part of the group
        if(!whatsappRepository.isPresent(group)){
            throw new Exception("Group does not exist");
        }
        User admin = whatsappRepository.getAdmin(group);
        if(!admin.equals(approver)){
            throw new Exception("Approver does not have rights");
        }
        List<User> users = whatsappRepository.getGroupUsersList(group);
        if(!users.contains(user)){
            throw new Exception("User is not a participant");
        }
        return whatsappRepository.changeAdmin(approver, user, group);
    }
}