package com.example.friends;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity
@TypeConverters({UserInUserConverter.class})
public class User {

    @NonNull
    @PrimaryKey
    private String id;
    private user user;
    private Messagetwo lastMessage;

    public User(String id, User.user user, Messagetwo lastMessage) {
        this.id = id;
        this.user = user;
        this.lastMessage = lastMessage;
    }

    public User.user getUser() {
        return user;
    }

    @Nullable
    public Messagetwo getLastMessage() {
        return lastMessage;
    }

    public void setUser(User.user user) {
        this.user = user;
    }

    public void setLastMessage(Messagetwo lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getRealId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public static class user {
        private String username;
        private String profilePic;
        private String displayName;
        private String _id;

        public user(String username, String profilePic, String displayName, String _id) {
            this.username = username;
            this.profilePic = profilePic;
            this.displayName = displayName;
            this._id = _id;
        }

        public String get_id() {
            return _id;
        }

        public String getDisplayName() {
            return displayName;
        }

        public String getProfilePic() {
            return profilePic;
        }

        public String getUsername() {
            return username;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }
    }

    public static class Messagetwo {
        private String content;
        private String _id;
        private Sender sender;
        private String created;

        public Messagetwo(String content, String created, Sender sender, String _id) {
            this.content = content;
            this.created = created;
            this.sender = sender;
            this._id = _id;
        }

        public Sender getSender() {
            return sender;
        }

        public String getCreated() {
            return created;
        }

        public String getContent() {
            return content;
        }

        public String get_id() {
            return _id;
        }
    }

    public static class Sender {
        private String _id;
        private String username;

        public String get_id() {
            return _id;
        }

        public String getUsername() {
            return username;
        }
    }
}
