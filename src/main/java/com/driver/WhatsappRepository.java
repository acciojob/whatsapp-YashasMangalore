//package com.driver;
//
//import java.util.*;
//
//import com.driver.exceptions.CustomException;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public class WhatsappRepository {
//
//    //Assume that each user belongs to at most one group
//    //You can use the below, mentioned hashmaps or delete these and create your own.
//    private HashMap<Group, List<User>> groupUserMap;
//    private HashMap<Group, List<Message>> groupMessageMap;
//    private int customGroupCount;
//    private int messageId;
//    private HashMap<String,String> userMap;
//    private HashSet<Message> messageSet;
//
//    private HashMap<Message, User> senderMap;
//    private HashMap<Group, User> adminMap;
//    private HashSet<String> userMobile;
//
//
//    public WhatsappRepository()
//    {
//        this.groupMessageMap = new HashMap<Group, List<Message>>();
//        this.groupUserMap = new HashMap<Group, List<User>>();
//        this.senderMap = new HashMap<Message, User>();
//        this.adminMap = new HashMap<Group, User>();
//        this.userMobile = new HashSet<>();
//        this.userMap=new HashMap<>();
//        this.messageSet = new HashSet<>();
//        this.customGroupCount = 1;
//        this.messageId = 0;
//    }
//
//    public String createUser(String name, String mobile) throws Exception
//    {
//        if(userMap.containsKey(mobile))
//            throw new Exception("User already exists");
//        else
//        {
//            userMap.put(mobile,name);
//            return "SUCCESS";
//        }
//    }
//
//    public Group createGroup(List<User> users)
//    {
//        Group grp=null;
//        if(users.size()>2)
//        {
//            grp=new Group("Group "+customGroupCount,users.size());
//            customGroupCount++;
//            groupUserMap.put(grp,users);
//        }
//        else if (users.size()==2)
//        {
//            grp=new Group(users.get(1).getName(),2);
//            groupUserMap.put(grp,users);
//        }
//        return grp;
//    }
//
//    public int createMessage(String content)
//    {
//        Message m=new Message();
//        m.setContent(content);
//        messageSet.add(m);
//        messageId++;
//        return messageId;
//    }
//
//    Group getGroupByName(String name){
//        for(Group g : groupUserMap.keySet()){
//            if(g.getName().equals(name)){
//                return g;
//            }
//        }
//        return null;
//    }
//
//    public int sendMessage(Message message, User sender, Group group) throws Exception
//    {
//        Group g = new Group();
//        for(Group x : groupUserMap.keySet()){
//            if(x.getName().equals(group.getName())){
//                g = x;
//
//            }
//        }
//        if(groupUserMap.containsKey(g)){
//            boolean flag = false;
//            for(User user : groupUserMap.get(getGroupByName(group.getName()))){
//                if(user.getName().equals(sender.getName())) {
//                    flag = true;
//                    break;
//                }
//            }
//            if(flag){
//                if(groupMessageMap.containsKey(group)){
//                    List<Message> msgs = groupMessageMap.get(group);
//                    msgs.add(message);
//                    groupMessageMap.put(group,msgs);
//                    return groupMessageMap.get(group).size();
//                }else{
//                    List<Message> msgs= new ArrayList<>();
//                    msgs.add(message);
//                    groupMessageMap.put(group,msgs);
//                    return groupMessageMap.get(group).size();
//                }
//            }else{
//                throw new CustomException("You are not allowed to send message");
//            }
//        }else{
//            throw new CustomException("Group does not exist");
//        }
//    }
//
//    private void updateAttributes(Group group) {
//        // Update the number of participants in the group
//        List<User> participants = groupUserMap.get(group);
//        int numberOfParticipants = participants != null ? participants.size() : 0;
//        group.setNumberOfParticipants(numberOfParticipants);
//
//        // Update the last modified timestamp of the group
//        group.setLastModified(new Date());
//    }
//
//    public String changeAdmin(User approver, User user, Group group) throws Exception {
//        // Throw "Group does not exist" if the mentioned group does not exist
//        if (!groupUserMap.containsKey(group)) {
//            throw new CustomException("Group does not exist");
//        }
//
//        // Throw "Approver does not have rights" if the approver is not the current admin of the group
//        List<User> groupUsers = groupUserMap.get(group);
//        if (!group.isAdmin(approver)) {
//            throw new CustomException("Approver does not have rights");
//        }
//
//        // Throw "User is not a participant" if the user is not a part of the group
//        if (!groupUsers.contains(user)) {
//            throw new CustomException("User is not a participant");
//        }
//
//        // Change the admin of the group to "user"
//        group.changeAdmin(user);
//
//        return "SUCCESS";
//    }
//
//
//    public int removeUser(User user) throws Exception {
//        boolean userFound = false;
//
//        // Iterate through each group to find and remove the user
//        for (Map.Entry<Group, List<User>> entry : groupUserMap.entrySet()) {
//            Group group = entry.getKey();
//            List<User> userList = entry.getValue();
//
//            if (userList.contains(user)) {
//                userFound = true;
//
//                // If the user is an admin, throw an exception
//                if (group.isAdmin(user)) {
//                    throw new CustomException("Cannot remove admin");
//                } else {
//                    // If the user is not an admin, remove the user from the group
//                    userList.remove(user);
//
//                    // Remove all messages sent by this user from the group's message list
//                    List<Message> groupMessages = groupMessageMap.get(group);
//                    groupMessages.removeIf(message -> message.getSender().equals(user));
//
//                    // Remove all messages sent by this user from the message set
//                    messageSet.removeIf(message -> message.getSender().equals(user));
//
//                    // Update relevant attributes accordingly
//                    updateAttributes(group);
//                }
//            }
//        }
//
//        // If the user was not found in any group, throw an exception
//        if (!userFound) {
//            throw new CustomException("User not found");
//        }
//
//        return 1; // Or any relevant return value
//    }
//
//    public String findMessage(Date start, Date end, int K) throws Exception {
//        // Filter messages within the specified time range
//        List<Message> messagesInRange = new ArrayList<>();
//        for (Message message : messageSet) {
//            Date messageDate = message.getTimestamp();
//            if (messageDate.after(start) && messageDate.before(end))
//            {
//                messagesInRange.add(message);
//            }
//        }
//
//        // Sort the messages by timestamp in descending order
//        messagesInRange.sort(Collections.reverseOrder());
//
//        // Check if the number of messages within the time range is less than K
//        if (messagesInRange.size() < K) {
//            throw new Exception("K is greater than the number of messages");
//        }
//
//        // Get the Kth latest message
//        Message kthMessage = messagesInRange.get(K - 1);
//
//        return kthMessage.getContent();
//    }
//
//}


package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class WhatsappRepository {

    //Assume that each user belongs to at most one group
    //You can use the below mentioned hashmaps or delete these and create your own.
    private HashMap<Group, List<User>> groupUserMap;
    private HashMap<Group, List<Message>> groupMessageMap;
    private HashMap<Message, User> senderMap;
    private HashMap<Group, User> adminMap;
    private HashSet<String> userMobile;
    private int customGroupCount;
    private int messageId;

    public WhatsappRepository(){
        this.groupMessageMap = new HashMap<Group, List<Message>>();
        this.groupUserMap = new HashMap<Group, List<User>>();
        this.senderMap = new HashMap<Message, User>();
        this.adminMap = new HashMap<Group, User>();
        this.userMobile = new HashSet<>();
        this.customGroupCount = 0;
        this.messageId = 0;
    }

    public String createUser(String name, String mobile) {
        User user = new User();
        if(!userMobile.contains(mobile)){
            user.setName(name);
            user.setMobile(mobile);
            userMobile.add(mobile);
            return "SUCCESS";
        }
        return null;
    }

    public Group createGroup(List<User> users){
        int count = customGroupCount+1;
        List<User> list = users;
        int length = users.size();
        Group group = new Group();
        if(length > 2){
            String groupName = "Group "+count;
            group.setName(groupName);
            group.setNumberOfParticipants(length);
            groupUserMap.put(group,list);
            customGroupCount++;
        }else{
            String groupName = users.get(1).getName();
            group.setName(groupName);
            group.setNumberOfParticipants(length);
            groupUserMap.put(group,list);
        }
        adminMap.put(group,list.get(0));
        return group;
    }

    public int createMessage(String content){
        // The 'i^th' created message has message id 'i'.
        Message message = new Message();
        messageId = messageId + 1;
        message.setId(messageId);
        message.setContent(content);
        Date date = new Date();
        message.setTimestamp(date);
        return messageId;
    }

    public boolean isPresent(Group group){
        if(groupUserMap.containsKey(group)){
            return true;
        }
        return false;
    }

    public List<User> getGroupUsersList(Group group){
        return groupUserMap.get(group);
    }

    public int sendMessage(Message message, User sender, Group group){
        List<Message> list = groupMessageMap.getOrDefault(group,new ArrayList<>());
        list.add(message);
        groupMessageMap.put(group,list);
        senderMap.put(message,sender);
        return list.size();
    }

    public User getAdmin(Group group){
        return groupUserMap.get(group).get(0);
    }

    public String changeAdmin(User approver, User user, Group group){
        //remove admin from the admin map
        //add user in the admin map for the group
        adminMap.remove(group);
        adminMap.put(group,user);
        return "SUCCESS";
    }
}