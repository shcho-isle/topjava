package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.NamedEntity;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * GKislin
 * 15.06.2015.
 */
@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        for (int i = 0; i < 10; i++) {
            save(new User(
                    null
                    , "user-0" + i
                    , "user-0" + i + "@topjava.ua"
                    , "Password-Of-user-0" + i
                    , Role.ROLE_USER
            ));
        }
    }

    @Override
    public boolean delete(int id) {
        if (repository.containsKey(id)) {
            LOG.info("delete " + id);

            repository.remove(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public User save(User user) {
        LOG.info("save " + user);

        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
        }
        repository.put(user.getId(), user);
        return user;
    }

    @Override
    public User get(int id) {
        LOG.info("get " + id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        LOG.info("getAll");

        List<User> result = new ArrayList<>(repository.values());
        result.sort(Comparator.comparing(NamedEntity::getName));

        return result;
    }

    @Override
    public User getByEmail(String email) {
        LOG.info("getByEmail " + email);

        for (User user: repository.values())
            if (user.getEmail().equalsIgnoreCase(email))
                return user;

        return null;
    }
}
