import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
	MessageTest.class,
	MessageMementoTest.class,
	ChatHistoryTest.class,
	ChatServerTest.class,
	UserTest.class,
	SearchMessagesByUserTest.class
})
public class CompleteUnitTest {
}
