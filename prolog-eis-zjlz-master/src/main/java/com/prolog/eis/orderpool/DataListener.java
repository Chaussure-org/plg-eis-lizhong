package com.prolog.eis.orderpool;

import com.prolog.eis.orderpool.service.OrderPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataListener implements CommandLineRunner {
    @Autowired
    private OrderPoolService orderPoolService;
    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {
        orderPoolService.initOrderPool();
    }
}
