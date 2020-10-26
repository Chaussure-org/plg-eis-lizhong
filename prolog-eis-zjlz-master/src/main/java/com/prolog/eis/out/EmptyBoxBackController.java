package com.prolog.eis.out;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:空箱退库
 *三十功名尘与土，八千里路云和月
 * @date:2020/10/26 9:14
 * @author:SunPP
 */
@RestController
@RequestMapping("emptyBox")
public class EmptyBoxBackController {
    @RequestMapping("back")
    public void back() throws Exception{

    }
}
