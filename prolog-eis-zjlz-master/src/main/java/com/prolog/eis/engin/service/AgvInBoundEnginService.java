package com.prolog.eis.engin.service;

import com.prolog.eis.location.dao.AgvStoragelocationMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author SunPP
 * myMotto:三十功名尘与土，八千里路云和月
 * Description: agv 回库流程开发
 * @return
 * @date:2020/10/29 15:59
 */
public interface AgvInBoundEnginService {


    void AgvInBound() throws Exception;

    String computeInBoundArea() throws Exception;
}
