package Handler.SubscribePack;

import DB.LocationEntity;
import DB.SubscribeEntity;
import DB.WeatherForecastEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;

import java.util.List;

import static Service.MessageReciever.GetSession;

public class InsertWeatherThread implements Runnable {
    private final String info;
    private final Integer id_sub;

    public InsertWeatherThread(Integer id_sub, String info) {

        this.id_sub = id_sub;
        this.info = info;
    }


    @Override
    public void run() {
        Session session = GetSession();
        Transaction transaction= session.beginTransaction();
//        Query query = GetSession().createNativeQuery("SELECT * FROM public.location WHERE id_subscribe = \'"+id_sub+"\'");
        String HQLquery = "FROM LocationEntity where subscribeByIdSubscribe.idSubscribe = :paramName";
        Query query = session.createQuery(HQLquery);
        query.setParameter("paramName", id_sub);
        List<LocationEntity> list = query.getResultList();
        WeatherForecastEntity weatherForecastEntity = new WeatherForecastEntity(list.get(0), info);
        session.save(weatherForecastEntity);
        transaction.commit();
        session.close();

    }



}
