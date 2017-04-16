## Calories Management
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/cd2d28ab27424a6aa33badbccfcffaca)](https://www.codacy.com/app/pavlo-plynko/CaloriesManagement?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=shcho-isle/CaloriesManagement&amp;utm_campaign=Badge_Grade)
[![Dependency Status](https://dependencyci.com/github/shcho-isle/CaloriesManagement/badge)](https://dependencyci.com/github/shcho-isle/CaloriesManagement)
[![Build Status](https://travis-ci.org/shcho-isle/CaloriesManagement.svg?branch=master)](https://travis-ci.org/shcho-isle/CaloriesManagement)

The diary of calories eaten allows you to set the daily calorie limit and monitor its exceeding.

## Code Example
```java
public class JpaUtil {

    @PersistenceContext
    private EntityManager em;

    public void clear2ndLevelHibernateCache() {
        Session s = (Session) em.getDelegate();
        SessionFactory sf = s.getSessionFactory();
        sf.getCache().evictAllRegions();
    }
}
```
    
## Launching
- DB configuration: `src/main/resources/db/postgres.properties`
- Script to create tables: `src/main/resources/db/initDB.sql`
- Specify path for log in TOPJAVA_ROOT enviroment variable

## Deployed project
http://plynko.herokuapp.com