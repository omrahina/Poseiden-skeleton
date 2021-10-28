package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CurvePointService {

    @Autowired
    private CurvePointRepository curvePointRepository;

    public List<CurvePoint> getAllCurvePoint(){
        List<CurvePoint> curvePoints = curvePointRepository.findAll();
        if (!curvePoints.isEmpty()){
            log.info("All curve points retrieved.");
            return curvePoints;
        }
        log.error("No curve point found.");
        return List.of();
    }
     public CurvePoint getCurvePoint(int id){
         CurvePoint curvePoint = curvePointRepository.findCurvePointById(id);
         if (curvePoint != null){
             log.info("CurvePoint found.");
             return curvePoint;
         }
         log.error("No curvePoint found.");
         return null;
     }

    public CurvePoint addCurvePoint(CurvePoint curvePoint) {
        try{
            CurvePoint savedCurvePoint = curvePointRepository.save(curvePoint);
            log.info("CurvePoint successfully saved.");
            return savedCurvePoint;
        } catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }

    public CurvePoint updateCurvePoint(CurvePoint curvePoint) {
        CurvePoint curvePointToUpdate = curvePointRepository.findCurvePointById(curvePoint.getId());
        if (curvePointToUpdate != null){
            log.info("Update in progress ...");
            curvePointToUpdate.setCurveId(curvePoint.getCurveId());
            curvePointToUpdate.setTerm(curvePoint.getTerm());
            curvePointToUpdate.setValue(curvePoint.getValue());
            return curvePointRepository.save(curvePointToUpdate);
        }
        log.error("Unable to update the curvePoint.");
        return null;

    }

    public boolean deleteCurvePoint(int id){
        CurvePoint curvePoint = curvePointRepository.findCurvePointById(id);
        if (curvePoint != null){
            curvePointRepository.delete(curvePoint);
            log.info("CurvePoint successfully deleted.");
            return true;
        }
        log.error("Deletion failure");
        return false;
    }
}
