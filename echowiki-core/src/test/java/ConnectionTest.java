import org.echowiki.core.configuration.CoreConfiguration;
import org.echowiki.core.configuration.DatabaseConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {CoreConfiguration.class, DatabaseConfiguration.class})
public class ConnectionTest {

    @Inject
    private EntityManagerFactory entityManagerFactory;

    @Transactional
    @Test
    public void test() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

    }
}
