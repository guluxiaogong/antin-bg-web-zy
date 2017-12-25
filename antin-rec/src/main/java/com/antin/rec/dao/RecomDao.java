package com.antin.rec.dao;

import com.antin.rec.entity.DoctorIdenModel;
import com.antin.rec.entity.RecReservationHistoryModel;
import com.antin.rec.entity.RecomOutModel;

import java.util.List;

/**
 * Created by Administrator on 2017/8/16.
 */
public interface RecomDao {

    List<RecReservationHistoryModel> queryReservationHistoryById(String persionId);

    List<DoctorIdenModel> queryDoctorOut();

    List<RecomOutModel> getReservationNumber(String orgId, String deptCode,
                                             String doctorCoed);
}
