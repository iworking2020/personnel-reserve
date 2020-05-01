package ru.iworking.personnel.reserve.dao;

public class LearningHistoryDao {

    private static volatile LearningHistoryDao instance;

    public static LearningHistoryDao getInstance() {
        LearningHistoryDao localInstance = instance;
        if (localInstance == null) {
            synchronized (LearningHistoryDao.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new LearningHistoryDao();
                }
            }
        }
        return localInstance;
    }

    private LearningHistoryDao() {}

}
