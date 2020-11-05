package com.prolog.eis.utils;

import com.prolog.eis.dto.mcs.McsMoveTaskDto;
import com.prolog.eis.dto.rcs.RcsTaskDto;
import com.prolog.eis.dto.sas.SasMoveTaskDto;
import com.prolog.eis.dto.wcs.WcsLineMoveDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * @Author wangkang
 * @Description
 * @CreateTime 2020-10-22 10:40
 */
public class CacheListUtils {

    private static List<McsMoveTaskDto> mcslist= new Vector<>();

    private static List<SasMoveTaskDto> saslist= new Vector();

    private static List<WcsLineMoveDto> wcslist= new Vector();

    private static List<RcsTaskDto> rcslist= new Vector();

    public static List<McsMoveTaskDto> getMcslist() {
        return mcslist;
    }

    public static void setMcslist(List<McsMoveTaskDto> mcslist) {
        CacheListUtils.mcslist = mcslist;
    }

    public static List<SasMoveTaskDto> getSaslist() {
        return saslist;
    }

    public static void setSaslist(List<SasMoveTaskDto> saslist) {
        CacheListUtils.saslist = saslist;
    }

    public static List<WcsLineMoveDto> getWcslist() {
        return wcslist;
    }

    public static void setWcslist(List<WcsLineMoveDto> wcslist) {
        CacheListUtils.wcslist = wcslist;
    }

    public static List<RcsTaskDto> getRcslist() {
        return rcslist;
    }

    public static void setRcslist(List<RcsTaskDto> rcslist) {
        CacheListUtils.rcslist = rcslist;
    }
}
