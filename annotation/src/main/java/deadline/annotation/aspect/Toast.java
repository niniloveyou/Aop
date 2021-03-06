package deadline.annotation.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author deadline
 * @time 2018/4/24
 *
 * 结合Aspect实现
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface Toast {

}
