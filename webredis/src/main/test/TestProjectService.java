import com.personal.feng.project.fyp_user.pojo.User;
import com.personal.feng.project.fyp_user.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml","classpath*:springmvc.xml","classpath*:sqlMapperConfig.xml"})

public class TestProjectService {

    @Autowired
    private IUserService userService;

    @Test
    public void testGetUserBId(){
       User user = userService.getUserById(1);
       System.out.println("性別："+user.getsex());
    }




}
