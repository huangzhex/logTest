import com.hz.TbiSmsApiApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TbiSmsApiApplication.class)
public class HZTest {

    @Test
    public void info(){
        Assert.assertEquals(2,1);
    }
}
