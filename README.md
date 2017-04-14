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
Here `src/main/resources/db/` you can find DBs configs and initialization SQL scripts.

Before launching the application you should set `VM options` like that: `-Dspring.profiles.active="profile1,profile2"`

Allowable values for profile1: `jdbc`, `jpa`, `datajpa`.

Allowable values for profile2: `postgres`, `hsqldb`.


## Technology used
<a href="http://www.apache-maven.ru/">Maven</a>,
<a href="http://projects.spring.io/spring-security/">Spring Security</a>,
<a href="http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html">Spring MVC</a>,
<a href="http://projects.spring.io/spring-data-jpa/">Spring Data JPA</a>,
<a href="http://spring.io/blog/2014/05/07/preview-spring-security-test-method-security">Spring Security Test</a>,
<a href="http://hibernate.org/orm/">Hibernate ORM</a>,
<a href="http://hibernate.org/validator/">Hibernate Validator</a>,
<a href="http://www.slf4j.org/">SLF4J</a>,
<a href="https://github.com/FasterXML/jackson">REST(Jackson)</a>,
<a href="http://ru.wikipedia.org/wiki/JSP">JSP</a>,
<a href="http://en.wikipedia.org/wiki/JavaServer_Pages_Standard_Tag_Library">JSTL</a>,
<a href="http://tomcat.apache.org/">Apache Tomcat</a>,
<a href="http://www.webjars.org/">WebJars</a>,
<a href="http://datatables.net/">DataTables plugin</a>,
<a href="http://ehcache.org">Ehcache</a>,
<a href="http://www.postgresql.org/">PostgreSQL</a>,
<a href="http://junit.org/">JUnit</a>,
<a href="http://hamcrest.org/JavaHamcrest/">Hamcrest</a>,
<a href="http://jquery.com/">jQuery</a>,
<a href="http://ned.im/noty/">jQuery notification</a>,
<a href="http://getbootstrap.com/">Bootstrap(CSS/JS)</a>

## Deployed project
http://plynko.herokuapp.com