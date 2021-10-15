package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.BidListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Controller
@Slf4j
public class BidListController {
    // TODO: Inject Bid service
    @Autowired
    private BidListService bidListService;

    @RequestMapping("/bidList/list")
    public String home(Model model, @RequestParam(name = "error", required = false) String error) {
        // TODO: call service find all bids to show to the view

        log.info("Request all bids");
        List<BidList> bids = bidListService.findAllBids();
        if (!bids.isEmpty()){
            model.addAttribute("bidList", bids);
        }
        model.addAttribute("bid", new BidList());
        if (error != null){
            model.addAttribute("error", "No bid found for the specified id.");
        }
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(@ModelAttribute("bid") BidList bid) {
        log.info("Request add a new bid");
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid @ModelAttribute("bid") BidList bid, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return bid list
        log.info("Request validate a new bid");
        if (result.hasErrors()){
            log.error("Error(s) in the form");
            return "bidList/add";
        }
        bidListService.addBidList(bid);
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Bid by Id and to model then show to the form
        BidList bidList = bidListService.getBidList(id);
        if (bidList != null){
            model.addAttribute("bidList", bidList);
            return "bidList/update";
        }
        return "redirect:/bidList/list?error";

    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid @ModelAttribute("bidList") BidList bidList,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Bid and return list Bid
        bidList.setBidListId(id);
        if (result.hasErrors()){
            log.error("Error(s) in the form");
            model.addAttribute("bidList", bidList);
            return "bidList/update";
        }
        bidListService.updateBidList(bidList);
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Bid by Id and delete the bid, return to Bid list
        log.info("Request delete bid");
        bidListService.deleteBidList(id);
        return "redirect:/bidList/list";
    }
}
