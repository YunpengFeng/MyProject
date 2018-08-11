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
        User user = null;
        for(int i = 0;i<6;i++) {
            user = userService.getUserById(String.valueOf(i));
        }
       System.out.println("性別："+user.getsex());
    }

    @Test
    public void edituser(){
        User user = new User();
        user.setAge(10);
        user.setId("1");
        user.setsex("女");
        user.setUserName("馮雲鵬");
        userService.editUser(user);
        System.out.println("性別："+user.getsex());
    }

}
