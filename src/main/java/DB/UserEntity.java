package DB;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user", schema = "public")
public class UserEntity {
    @Id
    @Column(name = "id_chat", nullable = false, length = 40)
    private String ID_chat;
    @OneToMany(mappedBy = "user")
    private List<SubscribeEntity> subscribesByIdUser;

    public UserEntity() {
    }


    public UserEntity(String ID_chat) {
        this.ID_chat = ID_chat;
        subscribesByIdUser = new ArrayList<>();
    }


    public UserEntity(int idUser, String ID_chat, List<SubscribeEntity> subscribesByIdUser) {
        this.ID_chat = ID_chat;
        this.subscribesByIdUser = subscribesByIdUser;
    }

    public String getID_chat() {
        return ID_chat;
    }

    public void setID_chat(String idChat) {
        this.ID_chat = idChat;
    }

    public void addSub(SubscribeEntity SE){
        subscribesByIdUser.add(SE);
    }


    @Override
    public int hashCode() {
        return Objects.hash( ID_chat);
    }


    public List<SubscribeEntity> getSubscribesByIdUser() {
        return subscribesByIdUser;
    }

    public void setSubscribesByIdUser(List<SubscribeEntity> subscribesByIdUser) {
        this.subscribesByIdUser = subscribesByIdUser;
    }
}
