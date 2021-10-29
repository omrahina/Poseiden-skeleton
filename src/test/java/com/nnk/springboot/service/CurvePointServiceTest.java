package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class CurvePointServiceTest {

    @Mock
    private CurvePointRepository curvePointRepository;

    @InjectMocks
    private CurvePointService curvePointService;

    @Test
    public void return_all_curvePoints_ok(){
        when(curvePointRepository.findAll()).thenReturn(List.of(new CurvePoint()));
        List<CurvePoint> curvePoints = curvePointService.getAllCurvePoint();

        assertThat(curvePoints).isNotEmpty();
        assertThat(curvePoints).hasSize(1);
    }

    @Test
    public void return_empty_curvePoint_list(){
        when(curvePointRepository.findAll()).thenReturn(List.of());
        List<CurvePoint> curvePoints = curvePointService.getAllCurvePoint();

        assertThat(curvePoints).isEmpty();
    }

    @Test
    public void get_curvePoint_ok(){
        when(curvePointRepository.findCurvePointById(anyInt())).thenReturn(new CurvePoint());
        CurvePoint curvePoint = curvePointService.getCurvePoint(1);

        assertThat(curvePoint).isNotNull();
    }

    @Test
    public void get_curvePoint_failure(){
        when(curvePointRepository.findCurvePointById(anyInt())).thenReturn(null);
        CurvePoint curvePoint = curvePointService.getCurvePoint(1);

        assertThat(curvePoint).isNull();
    }

    @Test
    public void add_curvePoint_ok(){
        CurvePoint curvePoint = new CurvePoint();
        when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint);
        CurvePoint savedCurvePoint = curvePointService.addCurvePoint(curvePoint);

        assertThat(savedCurvePoint).isNotNull();
    }

    @Test
    public void update_curvePoint_ok(){
        CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);
        curvePoint.setId(1);
        CurvePoint curvePoint2 = new CurvePoint(15, 15d, 30d);
        when(curvePointRepository.findCurvePointById(anyInt())).thenReturn(curvePoint);
        when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint2);
        CurvePoint updatedCurvePoint = curvePointService.updateCurvePoint(curvePoint);

        assertThat(updatedCurvePoint.getCurveId()).isEqualTo(15);
    }

    @Test
    public void update_curvePoint_failure(){
        CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);
        curvePoint.setId(1);
        when(curvePointRepository.findCurvePointById(anyInt())).thenReturn(null);
        CurvePoint updatedCurvePoint = curvePointService.updateCurvePoint(curvePoint);

        assertThat(updatedCurvePoint).isNull();
    }

    @Test
    public void delete_curvePoint_ok(){
        CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);
        when(curvePointRepository.findCurvePointById(anyInt())).thenReturn(curvePoint);
        doNothing().when(curvePointRepository).delete(any(CurvePoint.class));
        boolean result = curvePointService.deleteCurvePoint(1);

        assertThat(result).isTrue();
    }

    @Test
    public void delete_curvePoint_failure(){
        when(curvePointRepository.findCurvePointById(anyInt())).thenReturn(null);
        boolean result = curvePointService.deleteCurvePoint(1);

        assertThat(result).isFalse();
    }
}
