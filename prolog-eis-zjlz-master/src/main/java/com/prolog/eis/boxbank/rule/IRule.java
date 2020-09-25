package com.prolog.eis.boxbank.rule;

import java.util.function.Function;

public interface IRule {

    boolean execute(StoreLocationDTO location,Function<StoreLocationDTO,Boolean> func) throws Exception;

}
