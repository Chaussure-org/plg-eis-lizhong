package com.prolog.eis.store;

import com.prolog.eis.ZjlzApplication;
import com.prolog.eis.dto.store.ContainerInfoDto;
import com.prolog.eis.dto.store.ContainerQueryDto;
import com.prolog.eis.page.service.IPageService;
import com.prolog.framework.core.pojo.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @author dengj
 * @version 1.0
 * @date 2020/11/10 15:41
 */
@SpringBootTest(classes = ZjlzApplication.class)
@RunWith(SpringRunner.class)
public class PageTest {
    @Autowired
    private IPageService pageService;
    @Test
    public void pageContainer(){
        System.out.println("aaaa");
        ContainerQueryDto containerQueryDto = new ContainerQueryDto();
        containerQueryDto.setPageNum(1);
        containerQueryDto.setPageSize(2);
//        containerQueryDto.setContainerNo("1");
        containerQueryDto.setEndTime(new Date());
        Page<ContainerInfoDto> containerPage = pageService.getContainerPage(containerQueryDto);
        System.out.println(containerPage);
    }
}
