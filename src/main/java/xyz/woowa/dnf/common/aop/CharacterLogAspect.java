package xyz.woowa.dnf.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class CharacterLogAspect {
    private static final int SEVER_ID_INDEX = 0;
    private static final int CHARACTER_ID_INDEX = 1;

    @Around("@annotation(characterLog)")
    public Object logCharacter(ProceedingJoinPoint joinPoint,
                               CharacterLog characterLog) throws Throwable {

        String action = characterLog.value();
        Object[] args = joinPoint.getArgs();
        String method = joinPoint.getSignature().toShortString();

        String serverId = String.valueOf(args[SEVER_ID_INDEX]);
        String characterId = String.valueOf(args[CHARACTER_ID_INDEX]);

        long start = System.currentTimeMillis();
        log.info("[{}] 시작 - {} serverId={}, characterId={}",
                action, method, serverId, characterId);

        try {
            Object result = joinPoint.proceed();
            long ms = System.currentTimeMillis() - start;
            log.info("[{}] 완료 - {} serverId={}, characterId={} ({} ms)",
                    action, method, serverId, characterId, ms);
            return result;

        } catch (Exception e) {
            long ms = System.currentTimeMillis() - start;
            log.error("[{}] 예외 - {} serverId={}, characterId={} ({} ms), ex={}",
                    action, method, serverId, characterId, ms, e.getMessage(), e);
            throw e;
        }
    }
}
