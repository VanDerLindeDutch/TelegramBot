package Handler.SubscribePack;

import DB.LocationEntity;
import DB.SubscribeEntity;
import DB.UserEntity;
import DB.WeatherForecastEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.telegram.telegrambots.meta.api.objects.Location;

import java.util.concurrent.Callable;

import static Service.MessageReciever.GetSession;

public class InsertUserSubLocThread implements Callable<Integer> {
    private final String chatID;
    private final Location location;

    public InsertUserSubLocThread(String chatID, Location location) {
        this.chatID = chatID;
        this.location = location;
    }

    @Override
    public Integer call() {
        int ret;
        UserEntity ue = GetSession().get(UserEntity.class, chatID);
        if (ue == null) {
            ret = addUser();
        } else {
            ret = addSubscribe(ue);
        }
        return ret;
//        Query query = GetSession().createNativeQuery("SELECT * FROM public.user WHERE id_chat = \'"+chatID+"\'");

    }


    private int addUser() {
        Session session = GetSession();
        Transaction transaction = session.beginTransaction();
        UserEntity userEntity = new UserEntity(chatID);
        LocationEntity locationEntity = new LocationEntity(location.getLongitude(), location.getLatitude());
        SubscribeEntity subscribeEntity = new SubscribeEntity();
        subscribeEntity.setUser(userEntity);
        locationEntity.setSubscribeByIdSubscribe(subscribeEntity);
        session.save(userEntity);
        session.save(subscribeEntity);
        session.save(locationEntity);
        transaction.commit();
        session.close();
        return subscribeEntity.getIdSubscribe();
    }


    private int addSubscribe(UserEntity userEntity) {
        Session session = GetSession();
        Transaction transaction = session.beginTransaction();
        SubscribeEntity subscribeEntity = new SubscribeEntity();
        LocationEntity locationEntity = new LocationEntity(location.getLongitude(), location.getLatitude());
        locationEntity.setSubscribeByIdSubscribe(subscribeEntity);
        subscribeEntity.setUser(userEntity);
        session.save(subscribeEntity);
        session.save(locationEntity);
        transaction.commit();
        session.close();
        return subscribeEntity.getIdSubscribe();
    }
}
