package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BidListServiceTest {

    @Mock
    private BidListRepository bidListRepository;

    @InjectMocks
    private BidListService bidListService;

    @Test
    public void should_return_all_bids(){
        when(bidListRepository.findAll()).thenReturn(List.of(new BidList("Account Test", "Type Test", 10d)));
        List<BidList> bids = bidListService.findAllBids();

        assertThat(bids).isNotEmpty();
        assertThat(bids).hasSize(1);
    }

    @Test
    public void should_return_empty_bid_list(){
        when(bidListRepository.findAll()).thenReturn(List.of());
        List<BidList> bids = bidListService.findAllBids();

        assertThat(bids).isEmpty();
    }

    @Test
    public void should_add_a_bid(){
        BidList bid = new BidList();
        when(bidListRepository.save(any(BidList.class))).thenReturn(bid);
        BidList savedBid = bidListService.addBidList(bid);

        assertThat(savedBid).isNotNull();
    }

    @Test
    public void should_return_bid(){
        when(bidListRepository.findBidListByBidListId(anyInt())).thenReturn(new BidList("Account Test", "Type Test", 10d));
        BidList bid = bidListService.getBidList(1);

        assertThat(bid).isNotNull();
        assertThat(bid.getAccount()).isEqualTo("Account Test");
    }

    @Test
    public void get_bid_failure(){
        when(bidListRepository.findBidListByBidListId(anyInt())).thenReturn(null);
        BidList bid = bidListService.getBidList(1);

        assertThat(bid).isNull();
    }

    @Test
    public void should_update_bid(){
        BidList bid = new BidList("Account Test", "Type Test", 10d);
        bid.setBidListId(2);
        BidList bid2 = new BidList("Updated Account", "Type Test", 10d);
        when(bidListRepository.findBidListByBidListId(anyInt())).thenReturn(bid);
        when(bidListRepository.save(any(BidList.class))).thenReturn(bid2);
        BidList updatedBid = bidListService.updateBidList(bid);

        assertThat(updatedBid).isNotNull();
        assertThat(updatedBid.getAccount()).isEqualTo("Updated Account");
    }

    @Test
    public void should_fail_update_bid(){
        BidList bid = new BidList("Account Test", "Type Test", 10d);
        bid.setBidListId(2);
        when(bidListRepository.findBidListByBidListId(anyInt())).thenReturn(null);
        BidList updatedBid = bidListService.updateBidList(bid);

        assertThat(updatedBid).isNull();
    }

    @Test
    public void should_delete_bid(){
        BidList bid = new BidList("Account Test", "Type Test", 10d);
        when(bidListRepository.findBidListByBidListId(anyInt())).thenReturn(bid);
        doNothing().when(bidListRepository).delete(any(BidList.class));
        boolean result = bidListService.deleteBidList(1);

        assertThat(result).isTrue();
    }

    @Test
    public void should_fail_delete_bid(){
        when(bidListRepository.findBidListByBidListId(anyInt())).thenReturn(null);
        boolean result = bidListService.deleteBidList(1);

        assertThat(result).isFalse();
    }
}
