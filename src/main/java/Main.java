import DB.*;
import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.hibernate.cfg.Configuration;

import static Service.MessageReciever.GetSession;

public class Main {
    private static final SessionFactory SESSION_FACTORY;

    static {
        try {
//              SESSION_FACTORY = new Configuration().configure().buildSessionFactory();
            Configuration configuration = new Configuration().configure();
            configuration.addAnnotatedClass(UserEntity.class);
            configuration.addAnnotatedClass(LocationEntity.class);
            configuration.addAnnotatedClass(SubscribeEntity.class);
            StandardServiceRegistryBuilder serviceBuilder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
            SESSION_FACTORY = configuration.buildSessionFactory(serviceBuilder.build());

        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return SESSION_FACTORY.openSession();
    }



    public static void main(final String[] args) throws Exception {
        final Session session = getSession();
        Query query = GetSession().createNativeQuery("SELECT id_chat FROM public.user");
        System.out.println(query.getResultList());

//        try {
//            System.out.println("querying all the managed entities...");
//            final Metamodel metamodel = session.getSessionFactory().getMetamodel();
//            for (EntityType<?> entityType : metamodel.getEntities()) {
//                final String entityName = entityType.getName();
//                final Query query = session.createQuery("from " + entityName);
//                System.out.println("executing: " + query.getQueryString());
//                for (Object o : query.list()) {
//                    System.out.println("  " + o);
//                }
//            }
//        } finally {
//            session.close();
//        }
    }


}