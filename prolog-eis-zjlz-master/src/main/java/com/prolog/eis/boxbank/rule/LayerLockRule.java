package com.prolog.eis.boxbank.rule;

import com.prolog.eis.util.StoreLockHelper;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class LayerLockRule implements IRule {

    @Override
    public boolean execute(StoreLocationDTO location, Function<StoreLocationDTO,Boolean> func) throws Exception{
        Object lockObj = StoreLockHelper.getInstance().GetXKKunCunLockObj(location.getLayer());
        synchronized (lockObj) {
            try {
                if(func!=null) {
                    return func.apply(location);
                }
                return false;
            } catch (Exception e) {
                return false;
            }
        }
    }

}
