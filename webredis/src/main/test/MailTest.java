import com.personal.feng.utils.mail.Email;

import com.personal.feng.utils.mail.EmailImp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:account-email.xml"})


public class MailTest {
    @Resource(name = "EmailService")
    private Email EmailService;

    @Test
    public void testmail() {
        for(int i = 0 ; i<10; i++) {
            String subject = "尊敬的Miss Liu";
            String htmlText = "❤❤❤❤❤❤❤❤❤老婆❤❤❤❤最大❤❤❤❤❤❤❤宝宝❤❤❤❤❤最可爱❤❤❤❤❤❤❤❤❤❤❤❤";
            EmailService.sendMail("1565370422@qq.com", subject, htmlText);
        }
    }


}
