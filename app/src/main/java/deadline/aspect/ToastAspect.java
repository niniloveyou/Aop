package deadline.aspect;

import android.widget.Toast;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import deadline.App;


@Aspect
public class ToastAspect {

    @Pointcut("execution(@deadline.annotation.aspect.Toast * *(..))")//方法切入点
    public void methodAnnotated() {
    }

    @Around("methodAnnotated()")//在连接点进行方法替换
    public void aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        Toast.makeText(App.getContext(), "ToastAspect hook !!!", Toast.LENGTH_SHORT).show();
        joinPoint.proceed();//执行原方法
    }
}

