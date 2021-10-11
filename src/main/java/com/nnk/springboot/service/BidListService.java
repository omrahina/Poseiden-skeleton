package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BidListService {

    @Autowired
    private BidListRepository bidListRepository;


    public List<BidList> findAllBids(){
        List<BidList> bids = bidListRepository.findAll();

        if (!bids.isEmpty()){
            log.info("All bids retrieved.");
            return bids;
        }

        log.error("No bid found.");

        return List.of();
    }

    public BidList addBidList(BidList bid){
        try{
            BidList savedBid = bidListRepository.save(bid);
            log.info("Bid successfully saved.");
            return savedBid;
        } catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }

    public BidList getBidList(int id){
        BidList bid = bidListRepository.findBidListByBidListId(id);
        if(bid != null){
            log.info("Bid with Id "+ id +" found");
            return bid;
        }
        log.error("No bid found for id = "+ id);
        return null;
    }

    public BidList updateBidList(BidList bid){
        BidList bidToUpdate = bidListRepository.findBidListByBidListId(bid.getBidListId());
        if (bidToUpdate != null){
            log.info("Update in progress ...");
            bidToUpdate.setAccount(bid.getAccount());
            bidToUpdate.setType(bid.getType());
            bidToUpdate.setBidQuantity(bid.getBidQuantity());
            return bidListRepository.save(bidToUpdate);
        }
        log.error("Unable to update the bid");
        return null;
    }

    public void deleteBidList(BidList bid){
        bidListRepository.delete(bid);
        log.info("Bid deleted");
    }

    public void deleteBidList(int id){
        Optional<BidList> bid = bidListRepository.findById(id);
        if (bid.isPresent()){
            bidListRepository.delete(bid.get());
            log.info("Bid deleted");
        } else {
            log.error("No bid found. Deletion failure.");
        }
    }
}
