package DB;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "subscribe", schema = "public")
public class SubscribeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_subscribe", nullable = false)
    private int idSubscribe;
    @ManyToOne
    @JoinColumn(name = "id_chat", nullable = false)
    private UserEntity user;
    @OneToOne(mappedBy = "subscribeByIdSubscribe", cascade = CascadeType.ALL)
    private LocationEntity location;
    @Column(name = "is_stopped", nullable = false)
    private boolean isExecute;

    public SubscribeEntity() {
    }

    public SubscribeEntity(LocationEntity location) {
        this.location = location;
    }

    public int getIdSubscribe() {
        return idSubscribe;
    }

    public void setIdSubscribe(int idSubscribe) {
        this.idSubscribe = idSubscribe;
    }


    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity userByIdUser) {
        this.user = userByIdUser;
    }


    public LocationEntity getLocation() {
        return location;
    }

    public void setLocation(LocationEntity locationByIdLocation) {
        this.location = locationByIdLocation;
    }

    public boolean isExecute() {
        return isExecute;
    }

    public void setExecute(boolean execute) {
        isExecute = execute;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubscribeEntity that = (SubscribeEntity) o;
        return idSubscribe == that.idSubscribe;
    }

    @Override
    public String toString() {
        return "SubscribeEntity{" +
                "idSubscribe=" + idSubscribe +
                ", user=" + user +
                ", location=" + location.getIdLocation() +
                ", isExecute=" + isExecute +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSubscribe);
    }
}
